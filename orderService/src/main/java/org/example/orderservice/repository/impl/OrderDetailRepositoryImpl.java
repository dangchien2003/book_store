package org.example.orderservice.repository.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.orderservice.dto.request.BookMinusQuantityRequest;
import org.example.orderservice.entity.OrderDetail;
import org.example.orderservice.repository.OrderDetailRepository;
import org.example.orderservice.util.MapperUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailRepositoryImpl implements OrderDetailRepository {

    JdbcTemplate jdbcTemplate;

    @Override
    public int addAll(List<OrderDetail> orderDetail) {
        StringBuilder sql = new StringBuilder("""
                INSERT INTO order_detail(order_id, book_id, quantity, price, created_at)
                VALUES
                """);

        Object[] data = new Object[orderDetail.size() * 5];

        for (int i = 0; i < orderDetail.size(); i++) {
            sql.append(" (?, ?, ?, ?, ?) ");
            int start = i * 5;
            data[start] = orderDetail.get(i).getOrderId();
            data[start + 1] = orderDetail.get(i).getBookId();
            data[start + 2] = orderDetail.get(i).getQuantity();
            data[start + 3] = orderDetail.get(i).getPrice();
            data[start + 4] = orderDetail.get(i).getCreatedAt();
        }

        return jdbcTemplate.update(sql.toString(), data);
    }

    @Override
    public List<BookMinusQuantityRequest> getBookAndQuantityByOrderId(String orderId) throws Exception {
        String sql = """
                SELECT od.book_id, od.quantity
                FROM order_detail od
                WHERE od.order_id = ?
                """;

        return MapperUtils.mappingManyElement(BookMinusQuantityRequest.class,
                jdbcTemplate.queryForList(sql, orderId));
    }
}
