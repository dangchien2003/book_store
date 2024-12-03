package org.example.productservice.repository;

import org.example.productservice.dto.FindBook;
import org.example.productservice.dto.response.*;
import org.example.productservice.entity.Book;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BookRepository {
    Long create(Book book);

    List<ManagerFindBookResponse> find(FindBook filter) throws Exception;

    int countExistInIds(Set<Long> ids);

    List<BaseBookResponse> getAllBookInCategory(int id, int page, int pageSize) throws Exception;

    ManagerBookDetailResponse getDetail(long bookId) throws Exception;

    BookDetailForValidate getDetailForValidate(long bookId) throws Exception;

    List<DetailInternal> getListDetail(List<Long> bookIds) throws Exception;

    int updateQuantity(List<QuantityBookAfterMinusResponse> data, List<Long> ids);

    List<Book> findAllById(Collection<Long> ids) throws Exception;

    List<Book> findAll() throws Exception;
}
