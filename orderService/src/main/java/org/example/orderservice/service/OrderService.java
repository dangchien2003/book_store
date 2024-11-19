package org.example.orderservice.service;

import org.example.orderservice.dto.request.OrderCreationRequest;

public interface OrderService {
    void create(String uid, OrderCreationRequest request);
}
