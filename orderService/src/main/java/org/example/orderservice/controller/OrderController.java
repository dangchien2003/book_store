package org.example.orderservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.orderservice.dto.request.OrderCreationRequest;
import org.example.orderservice.dto.response.ApiResponse;
import org.example.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderController {

    OrderService orderService;

    @PostMapping("create")
    ApiResponse<Object> create(@Valid @NotNull(message = "user blank") @RequestHeader("user") String user,
                               @Valid @RequestBody OrderCreationRequest request) {
        orderService.create(user, request);
        return ApiResponse.<Object>builder()
                .result(null)
                .build();
    }
}
