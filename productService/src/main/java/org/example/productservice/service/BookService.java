package org.example.productservice.service;

import org.example.productservice.dto.request.*;
import org.example.productservice.dto.response.*;

import java.util.List;

public interface BookService {
    BookCreationResponse create(BookCreationRequest request);

    void update(BookUpdateRequest request);

    List<ManagerFindBookResponse> find(String name, Integer category, Integer publisher, Long author, int numberPage);

    ManagerBookDetailResponse mGetDetail(long bookId);

    List<DetailInternal> getDetailListBook(GetDetailListBookRequest request);

    List<QuantityBookAfterMinusResponse> minusQuantityBooks(List<BookMinusQuantityRequest> request);

    BookUploadImageResponse uploadImage(BookUploadImageRequest request);

    void removeImage(RemoveImageBookRequest request);
}
