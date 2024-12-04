package org.example.identityservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.example.identityservice.dto.request.LogoutRequest;
import org.example.identityservice.dto.request.RefreshTokenRequest;
import org.example.identityservice.dto.response.AuthenticationResponse;
import org.example.identityservice.dto.response.CheckTokenResponse;
import org.example.identityservice.dto.response.RefreshTokenClaimSet;
import org.example.identityservice.dto.response.RefreshTokenResponse;
import org.example.identityservice.entity.Token;
import org.example.identityservice.entity.User;
import org.example.identityservice.enums.TokenStatus;
import org.example.identityservice.enums.TokenType;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    UserRepository userRepository;
    TokenRepository tokenRepository;
    PasswordEncoder passwordEncoder;
    ObjectMapper objectMapper;

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
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request, String userAgent) {
        String newAccessToken;
        String uidInAccess;
        try {
            SignedJWT access = getTokenData(request.getAccessToken());
            SignedJWT refresh = verifyToken(request.getRefreshToken());

            uidInAccess = access.getJWTClaimsSet().getSubject();
            String scope = access.getJWTClaimsSet().getClaim("scope").toString();
            RefreshTokenClaimSet refreshTokenClaimSet = objectMapper.readValue(refresh.getJWTClaimsSet().getSubject(), RefreshTokenClaimSet.class);

            // compare data
            if (!uidInAccess.equals(refreshTokenClaimSet.getUid())
                    || !userAgent.equals(refreshTokenClaimSet.getUa())) {
                throw new AppException(ErrorCode.NO_ACCESS);
            }

            if (access.getJWTClaimsSet().getExpirationTime().toInstant().toEpochMilli() > Instant.now().toEpochMilli()) {
                logout(new LogoutRequest(null, request.getAccessToken()));
            }

            newAccessToken = UserUtils.genAccessToken(uidInAccess, scope, timeLiveAccessToken, secretKey);
        } catch (Exception e) {
            log.error("error: ", e);
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return RefreshTokenResponse.builder()
                .token(newAccessToken)
                .build();
    }


    SignedJWT getTokenData(String token) throws ParseException {
        return SignedJWT.parse(token);
    }

    @Override
    public void logout(LogoutRequest request) {
        try {
            List<Token> tokenReject = new ArrayList<>();
            tokenReject.add(genTokenBlackList(request.getAccessToken(), TokenType.ACCESS_TOKEN));
            if (request.getRefreshToken() != null) {
                tokenReject.add(genTokenBlackList(request.getRefreshToken(), TokenType.REFRESH_TOKEN));
            }
            tokenRepository.saveAll(tokenReject);
        } catch (Exception e) {
            log.error("logout error", e);
        }
    }

    Token genTokenBlackList(String token, TokenType tokenType) throws ParseException, JOSEException {
        SignedJWT jwt = null;
        jwt = verifyToken(token);
        long expire = jwt.getJWTClaimsSet().getExpirationTime().toInstant().toEpochMilli();
        String id = jwt.getJWTClaimsSet().getJWTID();
        return Token.builder()
                .tokenId(id)
                .type(tokenType)
                .expireAt(expire)
                .reject(TokenStatus.REJECT)
                .build();
    }

    @Override
    public AuthenticationResponse loginUsernameAndPassword(AuthenticationRequest request, String userAgent) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (Objects.isNull(user) ||
                !passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new AppException(ErrorCode.INVALID_USERNAME_PASSWORD);

        UserUtils.validateStatusUser(user);

        try {
            String refreshTokenClaimSet = objectMapper.writeValueAsString(new RefreshTokenClaimSet(user.getUid(), userAgent));
            return UserUtils.createAuthenticationResponse(user, refreshTokenClaimSet, secretKey, timeLiveAccessToken, timeLiveRefreshToken);
        } catch (JOSEException | JsonProcessingException e) {
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
                .existsByTokenIdAndReject(signedJWT.getJWTClaimsSet().getJWTID(), TokenStatus.REJECT) // check in black list
        ) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }
}
