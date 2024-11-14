package org.example.productservice.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productservice.dto.request.CategoryAddBookRequest;
import org.example.productservice.dto.request.RemoveBookInCategoryRequest;
import org.example.productservice.dto.response.ApiResponse;
import org.example.productservice.service.BookCategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("category")
public class BookCategoryController {

    BookCategoryService bookCategoryService;

    @PostMapping("add-book")
    ApiResponse<Void> addBook(@Valid @RequestBody CategoryAddBookRequest request) {
        bookCategoryService.addBook(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @DeleteMapping("remove-book")
    ApiResponse<Void> removeBook(@Valid @RequestBody RemoveBookInCategoryRequest request) {
        bookCategoryService.removeBooks(request);
        return ApiResponse.<Void>builder()
                .build();
    }

//    @DeleteMapping("delete-book")
//    ApiResponse<List<CategoryResponse>> find(@RequestParam("name") String name,
//                                             @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
//        return ApiResponse.<List<CategoryResponse>>builder()
//                .result(categoryService.find(name, page))
//                .build();
//    }
//
//    @GetMapping("info")
//    ApiResponse<List<CategoryResponse>> find1(@RequestParam("id") int id,
//                                              @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
//        return ApiResponse.<List<CategoryResponse>>builder()
//                .result(categoryService.find("name", page))
//                .build();
//    }


}
