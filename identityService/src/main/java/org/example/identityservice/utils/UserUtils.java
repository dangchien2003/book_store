package org.example.identityservice.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import org.example.identityservice.dto.response.AuthenticationResponse;
import org.example.identityservice.entity.Role;
import org.example.identityservice.entity.User;
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

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUid() + "*" + (user.getRole().isManager() ? "1" : "0"))
                .issuer("book_store")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(timeLive, ChronoUnit.MINUTES).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(new MACSigner(secretKey.getBytes()));

        return jwsObject.serialize();
    }

    public static String genRefreshToken(String userAgent, int timeLive, String secretKey) throws JOSEException {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userAgent)
                .issuer("book_store")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(timeLive, ChronoUnit.MINUTES).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);


        jwsObject.sign(new MACSigner(secretKey.getBytes()));
        return jwsObject.serialize();

    }

    public static AuthenticationResponse createAuthenticationResponse(User user, String userAgent, String secretKey, int timeLiveAccessToken, int timeLiveRefreshToken) throws JOSEException {
        return AuthenticationResponse.builder()
                .accessToken(genAccessToken(user, timeLiveAccessToken, secretKey))
                .refreshToken(genRefreshToken(userAgent, timeLiveRefreshToken, secretKey))
                .expire(timeLiveAccessToken * 60)
                .actor(identifyActor(user))
                .build();
    }

    static String identifyActor(User user) {
        
        String actor = null;

        if (!Objects.isNull(user.getRole()))
            actor = user.getRole().isManager() ? "Manager" : "Customer";

        return actor;
    }

    public static String buildScope(User user) {
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
