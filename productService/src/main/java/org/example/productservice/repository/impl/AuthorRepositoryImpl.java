package org.example.productservice.repository.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productservice.dto.response.AuthorResponse;
import org.example.productservice.entity.Author;
import org.example.productservice.repository.AuthorRepository;
import org.example.productservice.utils.MapperUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthorRepositoryImpl implements AuthorRepository {
    JdbcTemplate jdbcTemplate;

    @Override
    public Long create(Author author) {
        String sql = "INSERT INTO author(name, website, create_at) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, author.getName());
                ps.setString(2, author.getWebsite());
                ps.setLong(3, author.getCreatedAt());
                return ps;
            }, keyHolder);
        } catch (DataAccessException e) {
            return null;
        }

        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(Author author) {
        String sql = "UPDATE author SET name = ?, website = ?, modified_at=? WHERE id = ?";
        return jdbcTemplate.update(sql, author.getName(), author.getWebsite(), author.getModifiedAt(), author.getId());
    }

    @Override
    public Author findById(Long id) throws Exception {
        String sql = "SELECT * from author where id = ?";
        return MapperUtils.mappingOneElement(Author.class, jdbcTemplate.queryForMap(sql, id));
    }

    @Override
    public List<AuthorResponse> findAllOrderByName(int numberPage, int pageSize) throws Exception {
        String sql = "SELECT id, name, website FROM author ORDER BY name ASC limit ?, ?";
        return MapperUtils.mappingManyElement(
                AuthorResponse.class,
                jdbcTemplate.queryForList(sql, (numberPage - 1) * pageSize, pageSize));
    }

    @Override
    public boolean existById(Long id) {
        String key = "quantity";
        String sql = "SELECT count(*) as " + key + " from author where id = ?";
        return jdbcTemplate.queryForMap(sql, id)
                .get(key).toString().equals("1");
    }

    @Override
    public List<AuthorResponse> findAllByName(String name, int numberPage, int pageSize) throws Exception {
        String sql = "SELECT id, name, website FROM author WHERE name LIKE ? limit ?, ?";
        return MapperUtils.mappingManyElement(
                AuthorResponse.class,
                jdbcTemplate.queryForList(sql, "%" + name + "%", (numberPage - 1) * pageSize, pageSize));
    }
}
