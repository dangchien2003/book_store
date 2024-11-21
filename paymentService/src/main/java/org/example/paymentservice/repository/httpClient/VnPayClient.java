package org.example.paymentservice.repository.httpClient;

import feign.Headers;
import org.example.paymentservice.dto.request.VnPayCheckTransactionRequest;
import org.example.paymentservice.dto.response.VnPayCheckTransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "vnPay-server", url = "${vnPay.api-url}")
public interface VnPayClient {
    @PostMapping(consumes = "application/json")
    @Headers("Content-Type: application/json")
    VnPayCheckTransactionResponse checkTransaction(@RequestBody VnPayCheckTransactionRequest request);

}
