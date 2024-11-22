package org.example.orderservice.repository.httpClient;

import org.example.orderservice.dto.request.BookMinusQuantityRequest;
import org.example.orderservice.dto.request.GetDetailListBookRequest;
import org.example.orderservice.dto.response.ApiResponse;
import org.example.orderservice.dto.response.BookDetailInternal;
import org.example.orderservice.dto.response.QuantityBookAfterMinusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {
    @PostMapping("/product/internal/book/details")
    ApiResponse<List<BookDetailInternal>> getDetails(@RequestBody GetDetailListBookRequest request);

    @PutMapping("/product/internal/book/minus-quantity")
    ApiResponse<List<QuantityBookAfterMinusResponse>> minusBookQuantity(@RequestBody List<BookMinusQuantityRequest> request);
}
