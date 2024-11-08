package org.example.identityservice.controller;

import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.identityservice.dto.request.AuthenticationRequest;
import org.example.identityservice.dto.request.CheckTokenRequest;
import org.example.identityservice.dto.request.GoogleAuthenticationRequest;
import org.example.identityservice.dto.response.ApiResponse;
import org.example.identityservice.dto.response.AuthenticationResponse;
import org.example.identityservice.dto.response.CheckTokenResponse;
import org.example.identityservice.service.AuthenticationService;
import org.example.identityservice.service.GoogleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthController {

    AuthenticationService authenticationService;
    GoogleService googleService;

    @PostMapping("sign-in")
    ApiResponse<AuthenticationResponse> authentication(
            @RequestHeader("User-Agent") String userAgent,
            @Valid @RequestBody AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.loginUsernameAndPassword(request, userAgent))
                .build();
    }

    @PostMapping("google/verify")
    ApiResponse<AuthenticationResponse> googleAuthentication(
            @RequestHeader("User-Agent") String userAgent,
            @Valid @RequestBody GoogleAuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(googleService.googleAuthentication(request, userAgent))
                .build();
    }

    @PostMapping("/check-token")
    ApiResponse<CheckTokenResponse> checkToken(@Valid @RequestBody CheckTokenRequest request)
            throws JOSEException {
        return ApiResponse.<CheckTokenResponse>builder()
                .result(authenticationService.checkToken(request))
                .build();
    }

//    @PostMapping("/logout")
//    ApiResponse<Void> logout(@Valid @RequestBody LogoutRequest request) {
//        authenticationService.logout(request);
//        return ApiResponse.<Void>builder().build();
//    }
//
//    @PostMapping("/refresh")
//    ApiResponse<RefreshTokenResponse> logout(@Valid @RequestBody RefreshTokenRequest request)
//            throws JOSEException {
//        return ApiResponse.<RefreshTokenResponse>builder()
//                .result(authenticationService.refreshToken(request))
//                .build();
//    }
}
