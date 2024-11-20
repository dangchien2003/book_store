package org.example.paymentservice.controller.internal;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.orderservice.dto.response.ApiResponse;
import org.example.paymentservice.dto.request.CreateTransactionRequest;
import org.example.paymentservice.dto.response.TransactionCreationResponse;
import org.example.paymentservice.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("internal/transaction")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalTransactionController {

    TransactionService transactionService;

    @PostMapping
    ApiResponse<TransactionCreationResponse> create(@Valid @RequestBody CreateTransactionRequest request) {
        return ApiResponse.<TransactionCreationResponse>builder()
                .result(transactionService.create(request))
                .build();
    }
}
