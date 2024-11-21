package org.example.orderservice.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.orderservice.dto.request.OrderCreationRequest;
import org.example.orderservice.dto.response.TransactionCreationResponse;

public interface OrderService {
    TransactionCreationResponse create(HttpServletRequest httpServletRequest, String uid, OrderCreationRequest request);
}
