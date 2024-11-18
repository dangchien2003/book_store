package org.example.productservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.request.AddCartItemRequest;
import org.example.productservice.dto.response.BookDetailForValidate;
import org.example.productservice.dto.response.CartItemResponse;
import org.example.productservice.entity.Cart;
import org.example.productservice.enums.BookStatus;
import org.example.productservice.exception.AppException;
import org.example.productservice.exception.ErrorCode;
import org.example.productservice.mapper.CartMapper;
import org.example.productservice.repository.BookRepository;
import org.example.productservice.repository.CartRepository;
import org.example.productservice.service.CartService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@Slf4j
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    BookRepository bookRepository;
    CartMapper cartMapper;

    @Override
    public void add(String user, AddCartItemRequest request) {

        Cart cart = cartMapper.toCart(user, request);
        cart.onCreate();

        BookDetailForValidate detail;
        try {
            detail = bookRepository.getDetailForValidate(request.getBookId());
        } catch (EmptyResultDataAccessException e) {
            throw new AppException(ErrorCode.NOTFOUND_DATA);
        } catch (Exception e) {
            log.error("book repository getDetailForValidate error: ", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        if (detail.getQuantity() < request.getQuantity())
            throw new AppException(ErrorCode.QUANTITY_NOT_ENOUGH);

        if (!detail.getStatusCode().equals(BookStatus.ON_SALE.name()))
            throw new AppException(ErrorCode.ADD_TO_CART_FAILED);

        cartRepository.deleteAll(user, List.of(request.getBookId()));
        if (!cartRepository.create(cart))
            throw new AppException(ErrorCode.UPDATE_FAIL);
    }

    @Override
    public void remove(String user, long bookId) {
        cartRepository.deleteAll(user, List.of(bookId));
    }

    @Override
    public List<CartItemResponse> getAll(String user, int page) {
        int pageSize = 15;

        try {
            return cartRepository.getAll(user, page, pageSize);
        } catch (Exception e) {
            log.error("cart repository getAll error: ", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
