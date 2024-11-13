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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

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
}
