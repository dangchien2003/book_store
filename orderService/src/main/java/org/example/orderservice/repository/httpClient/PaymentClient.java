package org.example.orderservice.repository.httpClient;

import org.example.orderservice.dto.request.CreateTransactionRequest;
import org.example.orderservice.dto.response.ApiResponse;
import org.example.orderservice.dto.response.PaymentMethodDetail;
import org.example.orderservice.dto.response.TransactionCreationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {
    @GetMapping(value = "/payment/method/all")
    ApiResponse<List<PaymentMethodDetail>> getAllPaymentMethod();

    @PostMapping(value = "/payment/internal/transaction")
    ApiResponse<TransactionCreationResponse> createTransaction(@RequestBody CreateTransactionRequest request);
}
