package org.example.orderservice.controller.internal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.orderservice.dto.response.ApiResponse;
import org.example.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("internal")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderInternalController {
    OrderService orderService;

    @PutMapping("payment-success/{id}")
    ApiResponse<Void> orderPaymentSuccess(@PathVariable(name = "id") String orderId) {
        orderService.orderPaymentSuccess(orderId);
        return ApiResponse.<Void>builder().build();
    }
}
