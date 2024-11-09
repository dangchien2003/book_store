package org.example.productservice.service;

import org.example.productservice.dto.request.BookCreationRequest;
import org.example.productservice.dto.request.BookUpdateRequest;
import org.example.productservice.dto.response.BookCreationResponse;
import org.example.productservice.dto.response.BookUpdateResponse;

public interface BookService {
    BookCreationResponse create(BookCreationRequest request);

    BookUpdateResponse update(BookUpdateRequest request);
}
