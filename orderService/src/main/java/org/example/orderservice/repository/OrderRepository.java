package org.example.orderservice.repository;

import org.example.orderservice.entity.Order;

public interface OrderRepository {
    Integer create(Order order);

    int updateStatus(String orderId, long modifiedAt, String status);
}
