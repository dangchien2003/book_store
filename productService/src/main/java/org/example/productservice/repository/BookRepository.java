package org.example.productservice.repository;

import org.example.productservice.entity.Book;

public interface BookRepository {
    Long create(Book book);
}
