package org.example.productservice.repository.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.FindBook;
import org.example.productservice.dto.response.ManagerFindBookResponse;
import org.example.productservice.entity.Book;
import org.example.productservice.repository.BookRepository;
import org.example.productservice.utils.MapperUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Override
    public List<ManagerFindBookResponse> find(FindBook filter) throws Exception {
        StringBuilder sql = new StringBuilder(
                "SELECT b.id, b.name, b.main_image, b.price, b.discount, b.status_code, " +
                        "b.available_quantity, b.page_count, a.name as author_name FROM book b " +
                        "LEFT JOIN author a ON a.id = b.author_id WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        if (filter.getName() != null && !filter.getName().isEmpty()) {
            sql.append(" AND b.name LIKE ?");
            params.add("%" + filter.getName() + "%");
        }

        if (filter.getBookIds() != null && !filter.getBookIds().isEmpty()) {
            sql.append(" AND b.id IN (");
            sql.append(String.join(",", Collections.nCopies(filter.getBookIds().size(), "?")));
            sql.append(")");
            params.addAll(filter.getBookIds());
        }

        if (filter.getPublisher() != null) {
            sql.append(" AND b.publisher_id = ?");
            params.add(filter.getPublisher());
        }

        if (filter.getAuthor() != null) {
            sql.append(" AND b.author_id = ?");
            params.add(filter.getAuthor());
        }

        sql.append(" LIMIT ?, ?");
        params.add((filter.getNumberPage() - 1) * filter.getPageSize());
        params.add(filter.getPageSize());

        return MapperUtils.mappingManyElement(
                ManagerFindBookResponse.class,
                jdbcTemplate.queryForList(sql.toString(), params.toArray())
        );
    }
}
