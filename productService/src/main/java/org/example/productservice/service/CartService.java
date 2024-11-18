package org.example.productservice.service;

import org.example.productservice.dto.request.AddCartItemRequest;
import org.example.productservice.dto.response.CartItemResponse;

import java.util.List;

public interface CartService {
    void add(String user, AddCartItemRequest request);

    void remove(String user, long bookId);

    List<CartItemResponse> getAll(String user, int page);
}
