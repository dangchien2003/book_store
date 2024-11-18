package org.example.productservice.service;

import org.example.productservice.dto.request.AddCartItemRequest;

public interface CartService {
    void add(String user, AddCartItemRequest request);
}
