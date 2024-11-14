package org.example.productservice.repository.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.repository.BookCategoryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class BookCategoryRepositoryImpl implements BookCategoryRepository {
    JdbcTemplate jdbcTemplate;

    @Override
    public int create(int categoryId, Set<Long> bookIds, long createAt) {
        StringBuilder sql
                = new StringBuilder("INSERT INTO book_category (category_id, book_id, create_at) VALUES ");
        List<Object> params = new ArrayList<>();

        int index = 0;
        for (Long bookId : bookIds) {
            sql.append("(?, ?, ?)");
            if (index < bookIds.size() - 1) {
                sql.append(", ");
            }
            params.add(categoryId);
            params.add(bookId);
            params.add(createAt);
            index++;
        }
        ;

        return jdbcTemplate.update(sql.toString(), params.toArray());
    }

    public int removeBookInCategory(int categoryId, Set<Long> bookIds) {
        String sql = "DELETE FROM book_category WHERE category_id = :categoryId AND book_id IN (:bookIds)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("categoryId", categoryId);
        parameters.addValue("bookIds", bookIds);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        return namedParameterJdbcTemplate.update(sql, parameters);
    }
}
