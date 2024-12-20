package org.example.paymentservice.repository.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.paymentservice.entity.Transaction;
import org.example.paymentservice.enums.TransactionStatus;
import org.example.paymentservice.repository.TransactionRepository;
import org.example.paymentservice.util.MapperUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionRepositoryImpl implements TransactionRepository {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int create(Transaction transaction) {
        String sql = """
                INSERT INTO transaction(order_id, payment_method, amount, status, created_at)
                VALUES(:orderId, :paymentMethod, :amount, :status, :createdAt)
                """;

        return namedParameterJdbcTemplate.update(sql, MapperUtils.convertToMap(transaction));
    }

    @Override
    public int updateStatus(String orderId, TransactionStatus status, long modifiedAt) {
        String sql = """
                UPDATE transaction
                SET status = :status, modified_at = :modifiedAt
                WHERE order_id = :orderId
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("status", status.name());
        params.put("orderId", orderId);
        params.put("modifiedAt", modifiedAt);

        return namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Transaction getTransaction(String orderId) throws Exception {
        String sql = """
                SELECT amount, status, created_at
                FROM transaction
                WHERE order_id = :orderId
                """;

        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);

        return MapperUtils.mappingOneElement(Transaction.class,
                namedParameterJdbcTemplate.queryForMap(sql, params));
    }
}
