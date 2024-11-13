package org.example.productservice.mapper;

import org.example.productservice.dto.request.CategoryCreationRequest;
import org.example.productservice.dto.request.CategoryUpdateRequest;
import org.example.productservice.dto.response.CategoryResponse;
import org.example.productservice.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryCreationRequest request);

    Category toCategory(CategoryUpdateRequest request);

    CategoryResponse toCategoryResponse(Category category);
}
