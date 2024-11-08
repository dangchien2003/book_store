package org.example.identityservice.service;

import com.nimbusds.jose.JOSEException;
import org.example.identityservice.dto.request.AuthenticationRequest;
import org.example.identityservice.dto.request.CheckTokenRequest;
import org.example.identityservice.dto.response.AuthenticationResponse;
import org.example.identityservice.dto.response.CheckTokenResponse;

public interface AuthenticationService {
    AuthenticationResponse loginUsernameAndPassword(AuthenticationRequest request, String userAgent);

    CheckTokenResponse checkToken(CheckTokenRequest request) throws JOSEException;
}
