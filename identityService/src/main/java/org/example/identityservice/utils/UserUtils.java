package org.example.identityservice.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.example.identityservice.configuration.TokenTask;
import org.example.identityservice.dto.response.AuthenticationResponse;
import org.example.identityservice.entity.Role;
import org.example.identityservice.entity.Token;
import org.example.identityservice.entity.User;
import org.example.identityservice.enums.TokenStatus;
import org.example.identityservice.enums.TokenType;
import org.example.identityservice.exception.AppException;
import org.example.identityservice.exception.ErrorCode;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

public class UserUtils {

    private UserUtils() {
    }

    public static String genAccessToken(User user, int timeLive, String secretKey) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        String tokenId = UUID.randomUUID().toString();
        long expireTime = Instant.now().plus(timeLive, ChronoUnit.MINUTES).toEpochMilli();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUid())
                .issuer("book_store")
                .issueTime(new Date())
                .expirationTime(new Date(expireTime))
                .jwtID(tokenId)
                .claim("scope", UserUtils.buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(new MACSigner(secretKey.getBytes()));

        TokenTask.TOKEN_SAVE.add(new Token(tokenId, expireTime, TokenType.ACCESS_TOKEN, TokenStatus.NON_REJECT, user.getUid()));
        return jwsObject.serialize();
    }

    public static String genAccessToken(String uid, String scope, int timeLive, String secretKey) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        String tokenId = UUID.randomUUID().toString();
        long expireTime = Instant.now().plus(timeLive, ChronoUnit.MINUTES).toEpochMilli();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(uid)
                .issuer("book_store")
                .issueTime(new Date())
                .expirationTime(new Date(expireTime))
                .jwtID(tokenId)
                .claim("scope", scope)
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(new MACSigner(secretKey.getBytes()));

        TokenTask.TOKEN_SAVE.add(new Token(tokenId, expireTime, TokenType.ACCESS_TOKEN, TokenStatus.NON_REJECT, uid));
        return jwsObject.serialize();
    }

    public static String genRefreshToken(String uid, String refreshClaimSet, int timeLive, String secretKey) throws JOSEException {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        String tokenId = UUID.randomUUID().toString();
        long expireTime = Instant.now().plus(timeLive, ChronoUnit.MINUTES).toEpochMilli();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(refreshClaimSet)
                .issuer("book_store")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(timeLive, ChronoUnit.MINUTES).toEpochMilli()
                ))
                .jwtID(tokenId)
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(new MACSigner(secretKey.getBytes()));
        TokenTask.TOKEN_SAVE.add(new Token(tokenId, expireTime, TokenType.REFRESH_TOKEN, TokenStatus.NON_REJECT, uid));
        return jwsObject.serialize();

    }

    public static AuthenticationResponse createAuthenticationResponse(User user, String refreshClaimSet, String secretKey, int timeLiveAccessToken, int timeLiveRefreshToken) throws JOSEException {
        return AuthenticationResponse.builder()
                .accessToken(genAccessToken(user, timeLiveAccessToken, secretKey))
                .refreshToken(genRefreshToken(user.getUid(), refreshClaimSet, timeLiveRefreshToken, secretKey))
                .expire(timeLiveAccessToken * 60)
                .manager(UserUtils.identifyManager(user))
                .build();
    }

    static boolean identifyManager(User user) {

        boolean isManager = false;

        if (!Objects.isNull(user.getRole()))
            isManager = user.getRole().isManager();

        return isManager;
    }

    static String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        Role role = user.getRole();
        if (!Objects.isNull(user.getRole())) {
            stringJoiner.add("ROLE_" + role.getName());
            user.getRole().getPermissions().forEach(permission ->
                    stringJoiner.add(permission.getName())
            );
        }
        return stringJoiner.toString();
    }

    public static void validateStatusUser(User user) {
        switch (user.getStatusCode()) {
            case BLOCK -> throw new AppException(ErrorCode.ACCOUNT_LOCKED);
            case PENDING_VERIFIER -> throw new AppException(ErrorCode.ACCOUNT_PENDING_VERIFY);
            case ACTIVE -> {
            }
            default -> throw new AppException(ErrorCode.NO_ACCESS);
        }
    }
}
