package org.example.productservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productservice.dto.request.BookCreationRequest;
import org.example.productservice.dto.response.ApiResponse;
import org.example.productservice.dto.response.BookCreationResponse;
import org.example.productservice.dto.response.ManagerBookDetailResponse;
import org.example.productservice.dto.response.ManagerFindBookResponse;
import org.example.productservice.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/book")
public class BookController {
    BookService bookService;

    @PostMapping("add-new")
    ApiResponse<BookCreationResponse> createBook(@RequestBody BookCreationRequest request) {
        return ApiResponse.<BookCreationResponse>builder()
                .result(bookService.create(request))
                .build();
    }

    @GetMapping("find")
//    @PreAuthorize("hasAnyAuthority('ROLE_WAREHOUSE_MANAGER')")
    ApiResponse<List<ManagerFindBookResponse>> find(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) Integer category,
            @RequestParam(value = "publisher", required = false) Integer publisher,
            @RequestParam(value = "author", required = false) Long author,
            @RequestParam(value = "page", defaultValue = "1") int pageNumber

    ) {
        return ApiResponse.<List<ManagerFindBookResponse>>builder()
                .result(bookService.find(name, category, publisher, author, pageNumber))
                .build();
    }

    @GetMapping("/detail/{book_id}")
    ApiResponse<ManagerBookDetailResponse> getDetail(@PathVariable(name = "book_id") long bookId) {
        return ApiResponse.<ManagerBookDetailResponse>builder()
                .result(bookService.mGetDetail(bookId))
                .build();
    }
}
