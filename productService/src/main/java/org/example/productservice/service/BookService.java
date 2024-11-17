package org.example.productservice.service;

import org.example.productservice.dto.request.BookCreationRequest;
import org.example.productservice.dto.request.BookUpdateRequest;
import org.example.productservice.dto.response.BookCreationResponse;
import org.example.productservice.dto.response.BookUpdateResponse;
import org.example.productservice.dto.response.ManagerBookDetailResponse;
import org.example.productservice.dto.response.ManagerFindBookResponse;

import java.util.List;

public interface BookService {
    BookCreationResponse create(BookCreationRequest request);

    BookUpdateResponse update(BookUpdateRequest request);

    List<ManagerFindBookResponse> find(String name, Integer category, Integer publisher, Long author, int numberPage);

    ManagerBookDetailResponse mGetDetail(long bookId);
}
