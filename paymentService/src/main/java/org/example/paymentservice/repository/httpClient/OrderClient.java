package org.example.paymentservice.repository.httpClient;

import org.example.orderservice.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderClient {
    @PutMapping("/order/internal/payment-success/{id}")
    ApiResponse<Void> callPaymentSuccess(@PathVariable(name = "id") String orderId);
}
