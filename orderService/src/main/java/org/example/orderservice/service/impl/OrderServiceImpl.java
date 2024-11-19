package org.example.orderservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.dto.request.GetDetailListBookRequest;
import org.example.orderservice.dto.request.ItemOrder;
import org.example.orderservice.dto.request.OrderCreationRequest;
import org.example.orderservice.dto.response.ApiResponse;
import org.example.orderservice.dto.response.DetailInternal;
import org.example.orderservice.entity.Order;
import org.example.orderservice.enums.BookStatus;
import org.example.orderservice.enums.OrderStatus;
import org.example.orderservice.exception.AppException;
import org.example.orderservice.exception.ErrorCode;
import org.example.orderservice.repository.CommuneRepository;
import org.example.orderservice.repository.OrderRepository;
import org.example.orderservice.repository.httpClient.ProductClient;
import org.example.orderservice.service.OrderService;
import org.example.orderservice.util.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderServiceImpl implements OrderService {

    ProductClient productClient;
    OrderRepository orderRepository;
    CommuneRepository communeRepository;

    @Override
    public void create(String uid, OrderCreationRequest request) {

//        check payment method
        // get all payment method
        // validate

        if (validateQuantity(request.getItems()))
            throw new AppException(ErrorCode.PRODUCT_QUANTITY_IS_TOO_LARGE);

        if (!validateAddress(request.getCommuneAddressCode()))
            throw new AppException(ErrorCode.CUSTOM_MESSAGE);

        Set<Long> bookIds = request.getItems().stream()
                .map(ItemOrder::getBookId)
                .collect(Collectors.toSet());

        ApiResponse<List<DetailInternal>> detailsResponse;
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

        for (int i = 0; i < detailsResponse.getResult().size(); i++) {

            DetailInternal detailInternal = detailsResponse.getResult().get(i);
            ItemOrder itemOrder = sortedItems.get(i);

            validateStatus(detailInternal);

            validateQuantity(detailInternal, itemOrder);

            // calculator total
            total += detailInternal.getPrice() * itemOrder.getQuantity();
        }

        String orderId = RandomUtils.randomString(20, RandomUtils.PATTERN1);

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
    }

    void validateQuantity(DetailInternal detailInternal, ItemOrder itemOrder) {
        if (detailInternal.getQuantity() < itemOrder.getQuantity())
            throw new AppException(ErrorCode.INADEQUATE_QUANTITY, detailInternal.getName(), detailInternal.getQuantity());
    }

    void validateStatus(DetailInternal detailInternal) {
        if (!detailInternal.getStatusCode().equals(BookStatus.ON_SALE.name()))
            throw new AppException(ErrorCode.BOOK_NOT_RELEASED, detailInternal.getName());
    }

    boolean validateQuantity(Set<ItemOrder> items) {
        int sum = 0;
        for (ItemOrder item : items) {
            sum += item.getQuantity();
        }

        return sum > 10;
    }

    boolean validateAddress(int commune) {
        return communeRepository.existById(commune);
    }
}
