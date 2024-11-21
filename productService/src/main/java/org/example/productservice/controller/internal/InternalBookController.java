package org.example.productservice.controller.internal;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productservice.dto.request.BookMinusQuantityRequest;
import org.example.productservice.dto.request.GetDetailListBookRequest;
import org.example.productservice.dto.response.ApiResponse;
import org.example.productservice.dto.response.DetailInternal;
import org.example.productservice.dto.response.QuantityBookAfterMinusResponse;
import org.example.productservice.service.BookService;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("minus-quantity")
    ApiResponse<List<QuantityBookAfterMinusResponse>> minusQuantityBooks(@Valid @RequestBody List<BookMinusQuantityRequest> request) {
        return ApiResponse.<List<QuantityBookAfterMinusResponse>>builder()
                .result(bookService.minusQuantityBooks(request))
                .build();
    }
}
