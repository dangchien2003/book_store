package org.example.productservice.repository.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.response.CategoryResponse;
import org.example.productservice.entity.Category;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.utils.MapperUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(Category category) {
        String sql = "INSERT INTO category(name, create_at) " +
                "VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, category.getName());
                ps.setLong(2, category.getCreatedAt());
                return ps;
            }, keyHolder);
        } catch (Exception e) {
            log.error("Book repository error: ", e);
            return null;
        }

        return keyHolder.getKey().intValue();
    }

    @Override
    public int update(Category category) {
        String sql = "UPDATE category SET name = ?, modified_at = ? WHERE id = ?";
        return jdbcTemplate.update(sql, category.getName(), category.getModifiedAt(), category.getId());
    }

    @Override
    public List<CategoryResponse> findByName(String name, int pageNumber, int pageSize) throws Exception {
        String sql = "SELECT c.id, c.name FROM category c WHERE c.name LIKE ? LIMIT ?, ?";

        return MapperUtils.mappingManyElement(CategoryResponse.class,
                jdbcTemplate.queryForList(sql, "%" + name + "%", (pageNumber - 1) * pageSize, pageSize));
    }

    @Override
    public int countExistInIds(Set<Integer> ids) {
        String sql = "SELECT COUNT(*) as count FROM category WHERE id IN (:ids)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }
}
