package org.example.paymentservice.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.orderservice.dto.response.ApiResponse;
import org.example.paymentservice.dto.request.CreatePaymentMethodRequest;
import org.example.paymentservice.dto.request.EditActiveForPaymentMethodRequest;
import org.example.paymentservice.dto.response.PaymentMethodDetail;
import org.example.paymentservice.service.PaymentMethodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("method")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentMethodController {
    PaymentMethodService paymentMethodService;

    @PostMapping("create")
    ApiResponse<Void> create(@Valid @RequestBody CreatePaymentMethodRequest request) {
        paymentMethodService.create(request);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("all")
    ApiResponse<List<PaymentMethodDetail>> getAll() {
        return ApiResponse.<List<PaymentMethodDetail>>builder()
                .result(paymentMethodService.getAll())
                .build();
    }

    @PutMapping("edit/active/{id}")
    ApiResponse<Void> updateActive(@PathVariable(name = "id") String id,
                                   @Valid @RequestBody EditActiveForPaymentMethodRequest request) {
        paymentMethodService.updateActive(id, request);
        return ApiResponse.<Void>builder()
                .build();
    }
}
