package org.example.productservice.service;

import org.example.productservice.dto.request.CategoryCreationRequest;
import org.example.productservice.dto.request.CategoryUpdateRequest;
import org.example.productservice.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse create(CategoryCreationRequest request);

    CategoryResponse update(CategoryUpdateRequest request);

    List<CategoryResponse> find(String name, int page);
}
