package org.example.productservice.repository;

import org.example.productservice.dto.response.AuthorResponse;
import org.example.productservice.entity.Author;

import java.util.List;

public interface AuthorRepository {
    Long create(Author author);

    int update(Author author);

    Author findById(Long id) throws Exception;

    boolean existById(Long id);

    List<AuthorResponse> findAllOrderByName(int numberPage, int pageSize) throws Exception;

    List<AuthorResponse> findAllByName(String name, int numberPage, int pageSize) throws Exception;
}
