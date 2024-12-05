package org.example.apigateway.repository;


import org.example.apigateway.dto.request.IntrospectRequest;
import org.example.apigateway.dto.response.ApiResponse;
import org.example.apigateway.dto.response.CheckTokenResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IdentityClient {
    @PostExchange(url = "/auth/check-token", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<CheckTokenResponse>> checkToken(@RequestBody IntrospectRequest request);
}
