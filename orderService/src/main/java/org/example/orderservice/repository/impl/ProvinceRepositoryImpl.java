package org.example.orderservice.repository.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.orderservice.dto.response.ItemDataProvinceResponse;
import org.example.orderservice.repository.ProvinceRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProvinceRepositoryImpl implements ProvinceRepository {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void createAll(List<ItemDataProvinceResponse> data) {
        String sql = "INSERT INTO province (code, name) VALUES (:id, :full_name)";

        SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(data.toArray());
        namedParameterJdbcTemplate.batchUpdate(sql, batchParams);
    }
}
