package org.example.orderservice.service;

import org.example.orderservice.dto.request.OrderCreationRequest;
import org.example.orderservice.dto.response.TransactionCreationResponse;

public interface OrderService {
    TransactionCreationResponse create(String uid, OrderCreationRequest request);
}
