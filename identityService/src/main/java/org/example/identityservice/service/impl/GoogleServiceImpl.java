package org.example.identityservice.service.impl;

import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.example.identityservice.dto.request.GoogleAccessTokenRequest;
import org.example.identityservice.dto.request.GoogleAuthenticationRequest;
import org.example.identityservice.dto.request.UserCreationRequest;
import org.example.identityservice.dto.response.AuthenticationResponse;
import org.example.identityservice.dto.response.GoogleAccessTokenResponse;
import org.example.identityservice.dto.response.GoogleUserProfileResponse;
import org.example.identityservice.dto.response.UserCreationResponse;
import org.example.identityservice.entity.User;
import org.example.identityservice.exception.AppException;
import org.example.identityservice.exception.ErrorCode;
import org.example.identityservice.repository.UserRepository;
import org.example.identityservice.repository.httpClient.GoogleProfileClient;
import org.example.identityservice.repository.httpClient.GoogleTokenClient;
import org.example.identityservice.service.GoogleService;
import org.example.identityservice.utils.RandomUtils;
import org.example.identityservice.utils.UserUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GoogleServiceImpl implements GoogleService {
    UserServiceImpl userService;
    UserRepository userRepository;
    GoogleProfileClient googleProfileClient;
    GoogleTokenClient googleTokenClient;


    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    String clientId;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    String clientSecret;

    @NonFinal
    @Value("${token.time-live-access-token}")
    int timeLiveAccessToken;

    @NonFinal
    @Value("${token.time-live-refresh-token}")
    int timeLiveRefreshToken;

    @NonFinal
    @Value("${token.secret-key}")
    String secretKey;

    final String redirectUriForRegister = "http://localhost:3000/sign-up";
    final String redirectUriForAuth = "http://localhost:3000/auth";

    public GoogleUserProfileResponse getInfoGoogleAccount(String authorizationCode, String codeVerifier, String redirectUri) {
        GoogleAccessTokenRequest googleAccessTokenRequest =
                createBodyGoogleApiGetAccessToken(authorizationCode, codeVerifier, redirectUri);

        GoogleAccessTokenResponse response;
        try {
            response = googleTokenClient.getAccessToken(googleAccessTokenRequest);
        } catch (Exception e) {
            log.error("error: ", e);
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return googleProfileClient.getProfile("Bearer " + response.getAccess_token());
    }

    GoogleAccessTokenRequest createBodyGoogleApiGetAccessToken(String authorizationCode, String codeVerifier, String redirectUri) {
        return GoogleAccessTokenRequest.builder()
                .code(authorizationCode)
                .code_verifier(codeVerifier)
                .client_id(clientId)
                .client_secret(clientSecret)
                .redirect_uri(redirectUri)
                .build();
    }

    public AuthenticationResponse googleAuthentication(GoogleAuthenticationRequest request, String userAgent) {
        GoogleUserProfileResponse googleUserProfileResponse =
                getInfoGoogleAccount(request.getAuthorizationCode(), request.getCodeVerifier(), redirectUriForAuth);

        User user = userRepository.findByEmail(googleUserProfileResponse.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

        UserUtils.validateStatusUser(user);

        try {
            return UserUtils.createAuthenticationResponse(user, userAgent, secretKey, timeLiveAccessToken, timeLiveRefreshToken);
        } catch (JOSEException e) {
            log.error("Authentication error:", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public UserCreationResponse registerUserByGoogle(GoogleAuthenticationRequest request) {
        GoogleUserProfileResponse googleUserProfileResponse =
                getInfoGoogleAccount(request.getAuthorizationCode(), request.getCodeVerifier(), redirectUriForRegister);

        UserCreationRequest userCreationRequest = UserCreationRequest.builder()
                .email(googleUserProfileResponse.getEmail())
                .password(RandomUtils.randomPassword())
                .build();

        return userService.signUp(userCreationRequest, false);
    }
}
