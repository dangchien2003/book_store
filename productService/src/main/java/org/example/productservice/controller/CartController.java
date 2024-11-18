package org.example.productservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productservice.dto.request.AddCartItemRequest;
import org.example.productservice.dto.response.ApiResponse;
import org.example.productservice.dto.response.CartItemResponse;
import org.example.productservice.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/cart")
public class CartController {

    CartService cartService;

    @PostMapping("add")
    ApiResponse<Void> add(@RequestHeader(name = "user") String user, @Valid @RequestBody AddCartItemRequest request) {
        cartService.add(user, request);
        return ApiResponse.<Void>builder().build();
    }

    @DeleteMapping("remove/{bookId}")
    ApiResponse<Void> remove(@RequestHeader(name = "user") String user,
                             @PathVariable(name = "bookId") long bookId) {
        cartService.remove(user, bookId);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("all")
    ApiResponse<List<CartItemResponse>> all(@RequestHeader(name = "user") String user,
                                            @Valid @Min(value = 1, message = "INVALID_PAGE_NUMBER")
                                            @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        return ApiResponse.<List<CartItemResponse>>builder()
                .result(cartService.getAll(user, page))
                .build();
    }

}
