package org.example.productservice.repository;

import org.example.productservice.dto.response.CategoryResponse;
import org.example.productservice.entity.Category;

import java.util.List;

public interface CategoryRepository {
    Integer create(Category category);

    List<CategoryResponse> findByName(String name, int pageNumber, int pageSize) throws Exception;

    int update(Category category);
}
