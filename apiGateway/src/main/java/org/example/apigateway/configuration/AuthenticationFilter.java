package org.example.apigateway.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.example.apigateway.dto.response.ApiResponse;
import org.example.apigateway.dto.response.CheckTokenResponse;
import org.example.apigateway.exception.AppException;
import org.example.apigateway.exception.ErrorCode;
import org.example.apigateway.service.IdentityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    IdentityService identityService;

    @NonFinal
    @Value("${app.api-prefix}")
    String apiPrefix;

    String[] publicEndpoints = {
            "/identity/auth/sign-in",
            "/identity/auth/logout",
            "/identity/auth/refresh",
            "/identity/auth/check-token",
            "/identity/auth/google/verify",
            "/identity/user/sign-up",
            "/identity/user/register/google",
//            "/product/book/upload/image"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if (isPublicEndpoint(exchange.getRequest())) {
            return chain.filter(exchange);
        }

        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);

        if (CollectionUtils.isEmpty(authHeader)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = authHeader.get(0).replace("Bearer ", "");

        Mono<ApiResponse<CheckTokenResponse>> result = identityService.checkToken(token);

        return result.flatMap(introspectResponseApiResponse -> {
            if (introspectResponseApiResponse.getResult().isValid()) {
                String user = introspectResponseApiResponse.getResult().getUser();
                ServerHttpRequest modifiedRequest = exchange.getRequest()
                        .mutate()
                        .header("user", user)
                        .build();
                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            } else
                throw new AppException(ErrorCode.UNAUTHENTICATED);
        }).onErrorResume(throwable -> {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        });
    }


    private boolean isPublicEndpoint(ServerHttpRequest request) {
        return Arrays.stream(publicEndpoints).anyMatch(s ->
                request.getURI().getPath().matches(apiPrefix + s));
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Bean
    CorsWebFilter corsWebFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);

        return new CorsWebFilter(urlBasedCorsConfigurationSource);
    }
}
