package org.example.productservice.repository.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.entity.Book;
import org.example.productservice.repository.BookRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class BookRepositoryImpl implements BookRepository {
    JdbcTemplate jdbcTemplate;

    @Override
    public Long create(Book book) {
        String sql = "INSERT INTO book(name, reprint_edition, price, cost_price, discount, publisher_id, " +
                "author_id, page_count, size, available_quantity, description, status_code, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, book.getName());
                ps.setInt(2, book.getReprintEdition());
                ps.setInt(3, book.getPrice());
                ps.setInt(4, book.getCostPrice());
                ps.setInt(5, book.getDiscount());
                ps.setObject(6, book.getPublisherId());
                ps.setObject(7, book.getAuthorId());
                ps.setInt(8, book.getPageCount());
                ps.setString(9, book.getSize());
                ps.setInt(10, book.getAvailableQuantity());
                ps.setString(11, book.getDescription());
                ps.setString(12, book.getStatusCode());
                ps.setLong(13, book.getCreatedAt());
                return ps;
            }, keyHolder);
        } catch (Exception e) {
            log.error("Book repository error: ", e);
            return null;
        }

        return keyHolder.getKey().longValue();
    }
}
