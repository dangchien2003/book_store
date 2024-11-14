package org.example.productservice.service;

import org.example.productservice.dto.request.CategoryAddBookRequest;
import org.example.productservice.dto.request.RemoveBookInCategoryRequest;
import org.example.productservice.dto.response.DetailCategoryResponse;

public interface BookCategoryService {
    void addBook(CategoryAddBookRequest request);

    void removeBooks(RemoveBookInCategoryRequest request);

    DetailCategoryResponse detail(int id, int page);
}
