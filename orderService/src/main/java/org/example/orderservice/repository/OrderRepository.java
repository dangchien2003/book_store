package org.example.orderservice.repository;

import org.example.orderservice.entity.Order;

public interface OrderRepository {
    Integer create(Order order);
}
