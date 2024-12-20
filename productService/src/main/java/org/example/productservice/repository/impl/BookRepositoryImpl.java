package org.example.productservice.repository.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.FindBook;
import org.example.productservice.dto.response.*;
import org.example.productservice.entity.Book;
import org.example.productservice.repository.BookRepository;
import org.example.productservice.utils.MapperUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class BookRepositoryImpl implements BookRepository {
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean removeMainImage(long id, long modifiedAt) {
        String sql = """
                UPDATE book 
                SET main_image = null, modified_at = ?
                WHERE id = ?   
                """;
        return jdbcTemplate.update(sql, modifiedAt, id) == 1;
    }

    @Override
    public ImageBook getImageBook(long id) throws Exception {
        String sql = """
                SELECT main_image, other_image
                FROM book
                WHERE id = ?
                """;

        return MapperUtils.mappingOneElement(ImageBook.class, jdbcTemplate.queryForMap(sql, id));
    }

    @Override
    public boolean updateMainImageBook(String link, long id, long modifiedAt) {
        String sql = """
                UPDATE book 
                SET main_image = ?, modified_at = ?
                WHERE id = ?   
                """;
        return jdbcTemplate.update(sql, link, modifiedAt, id) == 1;
    }

    @Override
    public boolean updateChildImageBook(String data, long id, long modifiedAt) {
        String sql = """
                UPDATE book 
                SET other_image = ?, modified_at = ?
                WHERE id = ?   
                """;
        return jdbcTemplate.update(sql, data, modifiedAt, id) == 1;
    }

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

        if (filter.getBookIds() == null || filter.getBookIds().isEmpty()) {
            sql.append(" LIMIT ?, ?");
            params.add((filter.getNumberPage() - 1) * filter.getPageSize());
            params.add(filter.getPageSize());
        }

        return MapperUtils.mappingManyElement(
                ManagerFindBookResponse.class,
                jdbcTemplate.queryForList(sql.toString(), params.toArray())
        );
    }

    @Override
    public int countExistInIds(Set<Long> ids) {
        String sql = "SELECT COUNT(*) as count FROM book WHERE id IN (:ids)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }

    @Override
    public List<BaseBookResponse> getAllBookInCategory(int id, int page, int pageSize) throws Exception {
        String sql = """
                    WITH bookIds AS (
                        SELECT bc.book_id 
                        FROM book_category bc 
                        WHERE bc.category_id = ? 
                        LIMIT ?, ?
                    )
                    SELECT b.id, b.name, b.main_image 
                    FROM book b 
                    JOIN bookIds bi ON b.id = bi.book_id
                """;

        return MapperUtils.mappingManyElement(BaseBookResponse.class,
                jdbcTemplate.queryForList(sql, id, (page - 1) * pageSize, pageSize));
    }

    @Override
    public ManagerBookDetailResponse getDetail(long bookId) throws Exception {
        String sql = """
                SELECT b.id, b.name, b.main_image, b.price, b.discount, b.author_id, b.publisher_id, b.reprint_edition,
                b.other_image, b.status_code, b.available_quantity, b.page_count, b.size, b.description
                FROM book b
                WHERE b.id = ?
                """;

        return MapperUtils.mappingOneElement(ManagerBookDetailResponse.class,
                jdbcTemplate.queryForMap(sql, bookId)
        );
    }

    @Override
    synchronized public BookDetailForValidate getDetailForValidate(long bookId) throws Exception {
        String sql = """
                SELECT b.status_code, b.available_quantity as quantity
                FROM book b
                where b.id = ?
                """;

        return MapperUtils.mappingOneElement(BookDetailForValidate.class,
                jdbcTemplate.queryForMap(sql, bookId));
    }

    @Override
    public List<DetailInternal> getListDetail(List<Long> bookIds) throws Exception {
        StringBuilder sql = new StringBuilder("""
                SELECT b.id, b.name, b.price, b.available_quantity as quantity, b.status_code
                FROM book b
                WHERE b.id IN""");

        StringJoiner param = new StringJoiner(",", "(", ")");
        for (int i = 0; i < bookIds.size(); i++) {
            param.add("?");
        }
        sql.append(param);
        sql.append(" ORDER BY b.id ASC");

        Object[] params = new Object[bookIds.size()];
        for (int i = 0; i < bookIds.size(); i++) {
            params[i] = bookIds.get(i);
        }

        return MapperUtils.mappingManyElement(DetailInternal.class,
                jdbcTemplate.queryForList(sql.toString(), params));
    }

    @Override
    public int updateQuantity(List<QuantityBookAfterMinusResponse> data, List<Long> ids) {
        StringBuilder sql = new StringBuilder("""
                UPDATE book
                SET available_quantity = CASE
                """);

        Object[] params = new Object[ids.size() * 3];
        StringJoiner where = new StringJoiner(",", "(", ")");

        int currentParamsWhere = ids.size() * 2;
        int currentIndex = 0;
        for (int i = 0; i < ids.size(); i++) {
            sql.append(" WHEN id = ? THEN ? ");
            where.add("?");

            QuantityBookAfterMinusResponse itemData = data.get(i);
            params[currentIndex] = itemData.getBookId();
            params[currentIndex + 1] = itemData.getQuantity();
            params[currentParamsWhere] = itemData.getBookId();

            currentIndex += 2;
            currentParamsWhere += 1;
        }

        sql.append("""
                ELSE available_quantity
                END
                WHERE id IN
                """);
        sql.append(where);

        return jdbcTemplate.update(sql.toString(), params);
    }

    @Override
    public List<Book> findAllById(Collection<Long> ids) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT * FROM book WHERE id IN ");
        StringJoiner param = new StringJoiner(",", "(", ")");
        for (int i = 0; i < ids.size(); i++) {
            param.add("?");
        }
        sql.append(param);

        return MapperUtils.mappingManyElement(Book.class, jdbcTemplate.queryForList(sql.toString(), ids.toArray()));
    }

    @Override
    public List<Book> findAll() throws Exception {
        String sql = "SELECT * from book";
        return MapperUtils.mappingManyElement(Book.class, jdbcTemplate.queryForList(sql));
    }

    @Override
    public int updateBookDetail(Book book) {

        Object[] param = new Object[]{
                book.getName(), book.getReprintEdition(), book.getPrice(),
                book.getDiscount(), book.getPublisherId(), book.getAuthorId(),
                book.getPageCount(), book.getSize(), book.getAvailableQuantity(),
                book.getDescription(), book.getStatusCode(), book.getModifiedAt(),
                book.getId()};

        String sql = """
                UPDATE book set name = ?, reprint_edition = ?, price = ?, discount = ?, publisher_id = ?,
                 author_id = ?, page_count = ?, size = ?, available_quantity = ?, description = ?, status_code = ?, modified_at = ?
                 WHERE id = ?
                """;

        return jdbcTemplate.update(sql, param);
    }
}