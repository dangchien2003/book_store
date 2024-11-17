package org.example.productservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.request.CategoryCreationRequest;
import org.example.productservice.dto.request.CategoryUpdateRequest;
import org.example.productservice.dto.response.CategoryResponse;
import org.example.productservice.entity.Category;
import org.example.productservice.exception.AppException;
import org.example.productservice.exception.ErrorCode;
import org.example.productservice.mapper.CategoryMapper;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    static final int PAGE_SIZE_FOR_MANAGER_FIND = 2;

    @Override
    public CategoryResponse create(CategoryCreationRequest request) {
        Category category = categoryMapper.toCategory(request);
        category.onCreate();

        Integer id = categoryRepository.create(category);
        if (Objects.isNull(id))
            throw new AppException(ErrorCode.UPDATE_FAIL);

        category.setId(id);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse update(CategoryUpdateRequest request) {
        Category category = categoryMapper.toCategory(request);
        category.onUpdate();
        if (categoryRepository.update(category) != 1)
            throw new AppException(ErrorCode.UPDATE_FAIL);

        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> find(String name, int page) {
        try {
            if (Objects.isNull(name))
                return categoryRepository.findAll(page, PAGE_SIZE_FOR_MANAGER_FIND);
            return categoryRepository.findByName(name, page, PAGE_SIZE_FOR_MANAGER_FIND);
        } catch (Exception e) {
            log.error("Category repository error: ", e);
            throw new AppException(ErrorCode.NOTFOUND_DATA);
        }
    }
}
