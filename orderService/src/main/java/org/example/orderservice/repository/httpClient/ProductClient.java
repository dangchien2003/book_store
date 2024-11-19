package org.example.orderservice.repository.httpClient;

import org.example.orderservice.dto.request.GetDetailListBookRequest;
import org.example.orderservice.dto.response.ApiResponse;
import org.example.orderservice.dto.response.DetailInternal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {
    @PostMapping("/product/internal/book/details")
    ApiResponse<List<DetailInternal>> getDetails(@RequestBody GetDetailListBookRequest request);
}
