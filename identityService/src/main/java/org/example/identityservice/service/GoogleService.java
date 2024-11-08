package org.example.identityservice.service;

import org.example.identityservice.dto.request.GoogleAuthenticationRequest;
import org.example.identityservice.dto.response.AuthenticationResponse;
import org.example.identityservice.dto.response.GoogleUserProfileResponse;
import org.example.identityservice.dto.response.UserCreationResponse;

public interface GoogleService {
    GoogleUserProfileResponse getInfoGoogleAccount(String authorizationCode, String codeVerifier, String redirectUri);

    AuthenticationResponse googleAuthentication(GoogleAuthenticationRequest request, String userAgent);

    UserCreationResponse registerUserByGoogle(GoogleAuthenticationRequest request);
}
