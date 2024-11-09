package org.example.productservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productservice.dto.request.BookCreationRequest;
import org.example.productservice.dto.response.ApiResponse;
import org.example.productservice.dto.response.BookCreationResponse;
import org.example.productservice.service.BookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("book")
public class BookController {
    BookService bookService;

    @PostMapping("add-new")
    ApiResponse<BookCreationResponse> createBook(@RequestBody BookCreationRequest request) {
        return ApiResponse.<BookCreationResponse>builder()
                .result(bookService.create(request))
                .build();
    }
}
