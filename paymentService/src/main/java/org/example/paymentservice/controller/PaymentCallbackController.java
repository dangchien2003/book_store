package org.example.paymentservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.orderservice.dto.response.ApiResponse;
import org.example.paymentservice.dto.response.CheckTransactionResponse;
import org.example.paymentservice.service.VnPayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("callback")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentCallbackController {

    VnPayService vnpayService;

    @GetMapping("vn-pay/check")
    ApiResponse<CheckTransactionResponse> vnPayCheckTransaction(HttpServletRequest request) {

        return ApiResponse.<CheckTransactionResponse>builder()
                .result(vnpayService.queryTransaction(request))
                .build();
    }
}
