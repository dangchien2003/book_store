package org.example.orderservice.repository.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.orderservice.dto.response.ItemDataProvinceResponse;
import org.example.orderservice.repository.CommuneRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommuneRepositoryImpl implements CommuneRepository {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    JdbcTemplate jdbcTemplate;

    @Override
    public void createAll(List<ItemDataProvinceResponse> data, int district) {
        String sql = "INSERT INTO commune (code, name, district_code) VALUES (:id, :full_name, :district)";
        List<SqlParameterSource> batchParams = new ArrayList<>();
        for (ItemDataProvinceResponse item : data) {
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", item.getId())
                    .addValue("full_name", item.getFull_name())
                    .addValue("district", district);
            batchParams.add(params);
        }
        namedParameterJdbcTemplate.batchUpdate(sql, batchParams.toArray(new SqlParameterSource[0]));
    }

    @Override
    public boolean existById(int commune) {
        String sql = "CALL count_communce(?, ?)";
        Integer quantity = jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                CallableStatement callableStatement = con.prepareCall(sql);
                return callableStatement;
            }
        }, new CallableStatementCallback<Integer>() {
            @Override
            public Integer doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                cs.setObject(1, commune);
                cs.registerOutParameter(2, Types.INTEGER);
                cs.execute();
                return cs.getInt(2);
            }
        });
        return quantity == null || quantity == 1;
    }
}
