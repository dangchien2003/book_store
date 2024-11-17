package org.example.productservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productservice.dto.request.CategoryCreationRequest;
import org.example.productservice.dto.request.CategoryUpdateRequest;
import org.example.productservice.dto.response.ApiResponse;
import org.example.productservice.dto.response.CategoryResponse;
import org.example.productservice.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("category")
public class CategoryController {

    CategoryService categoryService;

    @PostMapping("add-new")
    ApiResponse<CategoryResponse> create(@Valid @RequestBody CategoryCreationRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.create(request))
                .build();
    }

    @PutMapping("update")
    ApiResponse<CategoryResponse> update(@Valid @RequestBody CategoryUpdateRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.update(request))
                .build();
    }

    @GetMapping("get")
    ApiResponse<List<CategoryResponse>> find(@RequestParam(name = "name", required = false) String name,
                                             @Valid @Min(value = 1, message = "INVALID_PAGE_NUMBER")
                                             @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.find(name, page))
                .build();
    }
}
