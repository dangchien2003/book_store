package org.example.identityservice.service.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.example.identityservice.dto.request.AuthenticationRequest;
import org.example.identityservice.dto.request.CheckTokenRequest;
import org.example.identityservice.dto.response.AuthenticationResponse;
import org.example.identityservice.dto.response.CheckTokenResponse;
import org.example.identityservice.entity.User;
import org.example.identityservice.enums.TokenStatus;
import org.example.identityservice.exception.AppException;
import org.example.identityservice.exception.ErrorCode;
import org.example.identityservice.repository.TokenRepository;
import org.example.identityservice.repository.UserRepository;
import org.example.identityservice.service.AuthenticationService;
import org.example.identityservice.utils.UserUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    UserRepository userRepository;
    TokenRepository tokenRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${token.time-live-access-token}")
    int timeLiveAccessToken;

    @NonFinal
    @Value("${token.time-live-refresh-token}")
    int timeLiveRefreshToken;

    @NonFinal
    @Value("${token.secret-key}")
    String secretKey;

    @Override
    public AuthenticationResponse loginUsernameAndPassword(AuthenticationRequest request, String userAgent) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (Objects.isNull(user) ||
                !passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new AppException(ErrorCode.INVALID_USERNAME_PASSWORD);

        UserUtils.validateStatusUser(user);

        try {
            return UserUtils.createAuthenticationResponse(user, userAgent, secretKey, timeLiveAccessToken, timeLiveRefreshToken);
        } catch (JOSEException e) {
            log.error("Authentication error:", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public CheckTokenResponse checkToken(CheckTokenRequest request)
            throws JOSEException {

        boolean isValid = true;

        try {
            verifyToken(request.getToken());
        } catch (AppException | ParseException e) {
            isValid = false;
        }

        return CheckTokenResponse.builder()
                .valid(isValid)
                .build();
    }

    SignedJWT verifyToken(String token)
            throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(secretKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        if (!verified
                || !expiryTime.after(new Date())
                || tokenRepository
                .existsByTokenIdAndReject(signedJWT.getJWTClaimsSet().getJWTID(), TokenStatus.REJECT)
        ) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }
}
