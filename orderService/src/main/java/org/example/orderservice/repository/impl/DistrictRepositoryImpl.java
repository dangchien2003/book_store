package org.example.orderservice.repository.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.orderservice.dto.response.ItemDataProvinceResponse;
import org.example.orderservice.repository.DistrictRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DistrictRepositoryImpl implements DistrictRepository {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void createAll(List<ItemDataProvinceResponse> data, int province) {
        String sql = "INSERT INTO district (code, name, province_code) VALUES (:id, :full_name, :province)";
        List<SqlParameterSource> batchParams = new ArrayList<>();
        for (ItemDataProvinceResponse item : data) {
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", item.getId())
                    .addValue("full_name", item.getFull_name())
                    .addValue("province", province);  // Thêm giá trị province vào trường province_code
            batchParams.add(params);
        }
        namedParameterJdbcTemplate.batchUpdate(sql, batchParams.toArray(new SqlParameterSource[0]));
    }
}
