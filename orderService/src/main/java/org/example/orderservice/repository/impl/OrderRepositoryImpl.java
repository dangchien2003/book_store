package org.example.orderservice.repository.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.entity.Order;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(Order order) {

        String sql = "CALL InsertOrder(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                CallableStatement callableStatement = con.prepareCall(sql);
                return callableStatement;
            }
        }, new CallableStatementCallback<Integer>() {
            @Override
            public Integer doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                cs.setString(1, order.getId());
                cs.setString(2, order.getPurchaseBy());
                cs.setInt(3, order.getCommuneAddressCode());
                cs.setString(4, order.getPaymentMethod());
                cs.setString(5, order.getStatusCode());
                cs.setString(6, order.getRecipientName());
                cs.setString(7, order.getPhoneNumber());
                cs.setString(8, order.getDetailAddress());
                cs.setInt(9, order.getTotal());
                cs.setLong(10, order.getCreatedAt());
                cs.registerOutParameter(11, Types.INTEGER);
                cs.execute();
                return cs.getInt(11);
            }
        });

    }
}
