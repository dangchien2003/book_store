package org.example.productservice.controller;

import org.example.productservice.dto.request.BookCreationRequest;
import org.example.productservice.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("book")
public class BookController {
    @PostMapping("add-new")
    ApiResponse<Object> createBook(@RequestBody BookCreationRequest request) {
        return null;
    }
}
