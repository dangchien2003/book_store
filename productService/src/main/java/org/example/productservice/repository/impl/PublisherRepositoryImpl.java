package org.example.productservice.repository.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productservice.dto.response.PublisherResponse;
import org.example.productservice.entity.Publisher;
import org.example.productservice.repository.PublisherRepository;
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
public class PublisherRepositoryImpl implements PublisherRepository {
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(Publisher publisher) {
        String sql = "INSERT INTO publisher(name, website, create_at) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, publisher.getName());
                ps.setString(2, publisher.getWebsite());
                ps.setLong(3, publisher.getCreatedAt());
                return ps;
            }, keyHolder);
        } catch (DataAccessException e) {
            return null;
        }

        return keyHolder.getKey().intValue();
    }

    @Override
    public int update(Publisher publisher) {
        String sql = "UPDATE publisher SET name = ?, website = ?, modified_at=? WHERE id = ?";
        return jdbcTemplate.update(sql, publisher.getName(), publisher.getWebsite(), publisher.getModifiedAt(), publisher.getId());
    }

    @Override
    public Publisher findById(Integer id) throws Exception {
        String sql = "SELECT * from publisher where id = ?";
        return MapperUtils.mappingOneElement(Publisher.class, jdbcTemplate.queryForMap(sql, id));
    }

    @Override
    public List<PublisherResponse> findAllOrderByName(int numberPage, int pageSize) throws Exception {
        String sql = "SELECT id, name, website FROM publisher ORDER BY name ASC limit ?, ?";
        return MapperUtils.mappingManyElement(
                PublisherResponse.class,
                jdbcTemplate.queryForList(sql, (numberPage - 1) * pageSize, pageSize));
    }

    @Override
    public boolean existById(Integer id) {
        String key = "quantity";
        String sql = "SELECT count(*) as " + key + " from publisher where id = ?";
        return jdbcTemplate.queryForMap(sql, id)
                .get(key).toString().equals("1");
    }

    @Override
    public List<PublisherResponse> findAllByName(String name, int numberPage, int pageSize) throws Exception {
        String sql = "SELECT id, name, website FROM publisher WHERE name LIKE ? limit ?, ?";
        return MapperUtils.mappingManyElement(
                PublisherResponse.class,
                jdbcTemplate.queryForList(sql, "%" + name + "%", (numberPage - 1) * pageSize, pageSize));
    }
}
