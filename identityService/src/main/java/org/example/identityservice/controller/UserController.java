package org.example.identityservice.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.identityservice.dto.request.GoogleAuthenticationRequest;
import org.example.identityservice.dto.request.UserCreationRequest;
import org.example.identityservice.dto.response.ApiResponse;
import org.example.identityservice.dto.response.UserCreationResponse;
import org.example.identityservice.service.GoogleService;
import org.example.identityservice.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {

    UserService userService;
    GoogleService googleService;

    @PostMapping("sign-up")
    ApiResponse<UserCreationResponse> signUp(@Valid @RequestBody UserCreationRequest request) {
        return ApiResponse.<UserCreationResponse>builder()
                .result(userService.signUp(request, false))
                .build();
    }

    @PostMapping("register/google")
    ApiResponse<UserCreationResponse> signUp(@Valid @RequestBody GoogleAuthenticationRequest request) {
        return ApiResponse.<UserCreationResponse>builder()
                .result(googleService.registerUserByGoogle(request))
                .build();
    }
}
