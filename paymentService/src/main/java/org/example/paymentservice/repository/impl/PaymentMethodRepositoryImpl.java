package org.example.paymentservice.repository.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.paymentservice.dto.response.PaymentMethodDetail;
import org.example.paymentservice.entity.PaymentMethod;
import org.example.paymentservice.repository.PaymentMethodRepository;
import org.example.paymentservice.util.MapperUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentMethodRepositoryImpl implements PaymentMethodRepository {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int create(PaymentMethod paymentMethod) {
        String sql = """
                INSERT INTO payment_method(id, name, description, active, create_at)
                VALUES(:id, :name, :description, :active, :createAt)
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("id", paymentMethod.getId());
        params.put("name", paymentMethod.getName());
        params.put("description", paymentMethod.getDescription());
        params.put("active", paymentMethod.getActive());
        params.put("createAt", paymentMethod.getCreatedAt());
        return namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public List<PaymentMethodDetail> getAll() throws Exception {
        String sql = """
                SELECT p.id, p.name, p.description
                FROM payment_method p
                WHERE p.active = 1
                ORDER BY p.create_at ASC
                """;
        return MapperUtils.mappingManyElement(PaymentMethodDetail.class,
                namedParameterJdbcTemplate.queryForList(sql, new HashMap<>()));
    }

    @Override
    public int editActive(String id, boolean active, long modifiedAt) {
        String sql = """
                UPDATE payment_method p
                SET p.active = :active, p.modified_at = :modifiedAt
                WHERE p.id = :id
                """;

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("active", active ? 1 : 0);
        params.put("modifiedAt", modifiedAt);
        return namedParameterJdbcTemplate.update(sql, params);
    }
}
