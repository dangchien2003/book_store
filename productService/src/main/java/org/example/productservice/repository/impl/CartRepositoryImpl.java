package org.example.productservice.repository.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.response.CartItemResponse;
import org.example.productservice.entity.Cart;
import org.example.productservice.repository.CartRepository;
import org.example.productservice.utils.MapperUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.StringJoiner;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class CartRepositoryImpl implements CartRepository {
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean create(Cart cart) {
        String sql = """
                INSERT INTO cart(user_id, book_id, quantity, create_at)
                VALUES (?, ?, ?, ?)
                """;

        return jdbcTemplate.update(sql, cart.getUserId(), cart.getBookId(), cart.getQuantity(), cart.getCreatedAt()) == 1;
    }

    @Override
    public void deleteAll(String user, List<Long> bookIds) {
        StringBuilder sql = new StringBuilder("DELETE FROM cart WHERE user_id = ? AND book_id IN(");
        StringJoiner param = new StringJoiner(",");
        for (int i = 0; i < bookIds.size(); i++) {
            param.add("?");
        }
        sql.append(param);
        sql.append(")");

        Object[] params = new Object[bookIds.size() + 1];
        params[0] = user;
        for (int i = 0; i < bookIds.size(); i++) {
            params[i + 1] = bookIds.get(i);
        }

        jdbcTemplate.update(sql.toString(), params);
    }

    @Override
    public List<CartItemResponse> getAll(String user, int page, int pageSize) throws Exception {
        String sql = """
                SELECT c.book_id, c.quantity, b.name as book_name, b.main_image as image, b.price, b.discount
                FROM cart c
                LEFT JOIN book b ON b.id = c.book_id
                WHERE c.user_id = ?
                LIMIT ?, ? 
                """;

        return MapperUtils.mappingManyElement(CartItemResponse.class,
                jdbcTemplate.queryForList(sql, user, (page - 1) * pageSize, pageSize));
    }
}
