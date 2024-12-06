package org.example.productservice.service;

import org.example.productservice.dto.request.BookCreationRequest;
import org.example.productservice.dto.request.BookMinusQuantityRequest;
import org.example.productservice.dto.request.BookUpdateRequest;
import org.example.productservice.dto.request.GetDetailListBookRequest;
import org.example.productservice.dto.response.*;

import java.util.List;

public interface BookService {
    BookCreationResponse create(BookCreationRequest request);

    void update(BookUpdateRequest request);

    List<ManagerFindBookResponse> find(String name, Integer category, Integer publisher, Long author, int numberPage);

    ManagerBookDetailResponse mGetDetail(long bookId);

    List<DetailInternal> getDetailListBook(GetDetailListBookRequest request);

    List<QuantityBookAfterMinusResponse> minusQuantityBooks(List<BookMinusQuantityRequest> request);
}
