package org.example.productservice.controller.internal;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productservice.dto.request.GetDetailListBookRequest;
import org.example.productservice.dto.response.ApiResponse;
import org.example.productservice.dto.response.DetailInternal;
import org.example.productservice.service.BookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/internal/book")
public class InternalBookController {
    BookService bookService;

    @PostMapping("details")
    ApiResponse<List<DetailInternal>> getDetailListBook(@Valid @RequestBody GetDetailListBookRequest request) {
        return ApiResponse.<List<DetailInternal>>builder()
                .result(bookService.getDetailListBook(request))
                .build();
    }
}
