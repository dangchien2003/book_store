package org.example.orderservice.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.dto.request.*;
import org.example.orderservice.dto.response.ApiResponse;
import org.example.orderservice.dto.response.BookDetailInternal;
import org.example.orderservice.dto.response.PaymentMethodDetail;
import org.example.orderservice.dto.response.TransactionCreationResponse;
import org.example.orderservice.entity.Order;
import org.example.orderservice.entity.OrderDetail;
import org.example.orderservice.enums.BookStatus;
import org.example.orderservice.enums.OrderStatus;
import org.example.orderservice.exception.AppException;
import org.example.orderservice.exception.ErrorCode;
import org.example.orderservice.mapper.OrderDetailMapper;
import org.example.orderservice.repository.CommuneRepository;
import org.example.orderservice.repository.OrderDetailRepository;
import org.example.orderservice.repository.OrderRepository;
import org.example.orderservice.repository.httpClient.PaymentClient;
import org.example.orderservice.repository.httpClient.ProductClient;
import org.example.orderservice.service.OrderService;
import org.example.orderservice.util.RandomUtils;
import org.example.orderservice.util.RequestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {

    ProductClient productClient;
    OrderRepository orderRepository;
    CommuneRepository communeRepository;
    OrderDetailRepository orderDetailRepository;
    OrderDetailMapper orderDetailMapper;
    PaymentClient paymentClient;

    @Override
    public void orderPaymentSuccess(String orderId) {

        List<BookMinusQuantityRequest> orderDetails;
        try {
            orderDetails = orderDetailRepository.getBookAndQuantityByOrderId(orderId);
        } catch (Exception e) {
            log.error("orderDetailRepository.getBookAndQuantityByOrderId error: ", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        String status = OrderStatus.SUCCESS.name();

        try {
            productClient.minusBookQuantity(orderDetails);
        } catch (Exception e) {
            log.error("minus book error: ", e);
            log.error("minus book error for: " + orderId);
            status = OrderStatus.MINUS_ERROR.name();
        }

        if (orderRepository.updateStatus(orderId, Instant.now().toEpochMilli(), status) != 1) {
            log.error("error update status success for order: " + orderId);
            throw new AppException(ErrorCode.UPDATE_FAIL);
        }

        if (status.equals(OrderStatus.MINUS_ERROR.name())) {
            throw new AppException(ErrorCode.MINUS_BOOK_ERROR);
        }
    }

    @Override
    public TransactionCreationResponse create(HttpServletRequest httpServletRequest, String uid, OrderCreationRequest request) {

        if (!checkAddressExistence(request.getCommuneAddressCode()))
            throw new AppException(ErrorCode.ADDRESS_NOT_FOUND);

        if (checkForOverstockedProducts(request.getItems()))
            throw new AppException(ErrorCode.PRODUCT_QUANTITY_IS_TOO_LARGE);

        if (!checkPhoneNumber(request.getPhoneNumber()))
            throw new AppException(ErrorCode.INVALID_PHONE_NUMBER);

        if (!checkPaymentMethodExistence(request.getPaymentMethod()))
            throw new AppException(ErrorCode.PAYMENT_METHOD_NOT_FOUND);

        Set<Long> bookIds = request.getItems().stream()
                .map(ItemOrder::getBookId)
                .collect(Collectors.toSet());

        ApiResponse<List<BookDetailInternal>> detailsResponse;
        try {
            detailsResponse = productClient.getDetails(new GetDetailListBookRequest(bookIds));
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        if (detailsResponse.getResult().size() < request.getItems().size())
            throw new AppException(ErrorCode.BOOK_NOT_FOUND);

        List<ItemOrder> sortedItems = request.getItems().stream()
                .sorted(Comparator.comparingLong(ItemOrder::getBookId))
                .toList();

        int total = 0;
        String orderId = RandomUtils.randomString(20, RandomUtils.PATTERN1);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (int i = 0; i < detailsResponse.getResult().size(); i++) {

            BookDetailInternal bookDetailInternal = detailsResponse.getResult().get(i);
            ItemOrder itemOrder = sortedItems.get(i);

            validateProductStatus(bookDetailInternal);

            validateProductExistence(bookDetailInternal, itemOrder);

            // calculator total
            total += bookDetailInternal.getPrice() * itemOrder.getQuantity();

            // convert to order detail
            orderDetails.add(convertOrderDetail(bookDetailInternal, orderId, itemOrder.getQuantity()));
        }


        Order order = Order.builder()
                .id(orderId)
                .purchaseBy(uid)
                .communeAddressCode(request.getCommuneAddressCode())
                .paymentMethod(request.getPaymentMethod())
                .statusCode(OrderStatus.CREATED.name())
                .recipientName(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .detailAddress(request.getDetailAddress())
                .total(total)
                .build();

        order.onCreate();

        if (orderRepository.create(order) < 1)
            throw new AppException(ErrorCode.UPDATE_FAIL);

        if (orderDetailRepository.addAll(orderDetails) != detailsResponse.getResult().size()) {
            orderRepository.updateStatus(orderId, Instant.now().toEpochMilli(), OrderStatus.ADD_DETAIL_ERROR.name());
            log.error("fail add order detail");
            throw new AppException(ErrorCode.UPDATE_FAIL);
        }

        return createTransaction(RequestUtils.getClientIP(httpServletRequest), order);
    }

    OrderDetail convertOrderDetail(BookDetailInternal book, String orderId, int quantity) {
        OrderDetail orderDetail = orderDetailMapper.toOrderDetail(book, orderId, quantity);
        orderDetail.onCreate(); // caafn tối ưu
        return orderDetail;
    }

    TransactionCreationResponse createTransaction(String ipAddress, Order order) {
        TransactionCreationResponse transaction = null;
        try {
            transaction = paymentClient.createTransaction(
                            new CreateTransactionRequest(order.getId(), order.getPaymentMethod(), order.getTotal(), ipAddress))
                    .getResult();
        } catch (Exception e) {
            log.error("paymentClient.createTransaction error: ", e);
        }

        String status;
        if (Objects.isNull(transaction)) {
            status = OrderStatus.TRANSACTION_ERROR.name();
        } else {
            status = OrderStatus.PAYMENT_PENDING.name();
        }

        if (orderRepository.updateStatus(order.getId(), Instant.now().toEpochMilli(), status) == 0) {
            log.warn("updateStatus error");
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        return transaction;
    }

    void validateProductExistence(BookDetailInternal bookDetailInternal, ItemOrder itemOrder) {
        if (bookDetailInternal.getQuantity() < itemOrder.getQuantity())
            throw new AppException(ErrorCode.INADEQUATE_QUANTITY, bookDetailInternal.getName(), bookDetailInternal.getQuantity());
    }

    void validateProductStatus(BookDetailInternal bookDetailInternal) {
        if (!bookDetailInternal.getStatusCode().equals(BookStatus.ON_SALE.name()))
            throw new AppException(ErrorCode.BOOK_NOT_RELEASED, bookDetailInternal.getName());
    }

    boolean checkForOverstockedProducts(Set<ItemOrder> items) {
        int sum = 0;
        for (ItemOrder item : items) {
            sum += item.getQuantity();
        }

        return sum > 10;
    }

    boolean checkAddressExistence(int commune) {
        return communeRepository.existById(commune);
    }

    boolean checkPaymentMethodExistence(String paymentMethod) {
        List<PaymentMethodDetail> methods = paymentClient.getAllPaymentMethod().getResult();

        for (PaymentMethodDetail method : methods) {
            if (method.getId().equals(paymentMethod)) {
                return true;
            }
        }

        return false;
    }

    boolean checkPhoneNumber(String phone) {
        return phone.matches("^0\\d{9}$");
    }
}
