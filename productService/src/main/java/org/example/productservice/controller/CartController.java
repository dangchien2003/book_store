package org.example.productservice.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productservice.dto.request.AddCartItemRequest;
import org.example.productservice.dto.response.ApiResponse;
import org.example.productservice.service.CartService;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("update")
    ApiResponse<Void> update(@Valid @RequestBody Object request) {
        return ApiResponse.<Void>builder().build();
    }

    @DeleteMapping("remove")
    ApiResponse<Void> remove(@Valid @RequestBody Object request) {
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("all")
    ApiResponse<Void> all(@Valid @RequestBody Object request) {
        return ApiResponse.<Void>builder().build();
    }

}
