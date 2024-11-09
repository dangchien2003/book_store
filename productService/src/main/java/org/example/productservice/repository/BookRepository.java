package org.example.productservice.repository;

import org.example.productservice.dto.FindBook;
import org.example.productservice.dto.response.ManagerFindBookResponse;
import org.example.productservice.entity.Book;

import java.util.List;

public interface BookRepository {
    Long create(Book book);

    List<ManagerFindBookResponse> find(FindBook filter) throws Exception;
}
