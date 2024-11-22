package org.example.orderservice.repository;

import org.example.orderservice.dto.request.BookMinusQuantityRequest;
import org.example.orderservice.entity.OrderDetail;

import java.util.List;

public interface OrderDetailRepository {
    int addAll(List<OrderDetail> orderDetail);

    List<BookMinusQuantityRequest> getBookAndQuantityByOrderId(String orderId) throws Exception;
}
