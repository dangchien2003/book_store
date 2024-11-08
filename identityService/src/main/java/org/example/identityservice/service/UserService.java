package org.example.identityservice.service;

import org.example.identityservice.dto.request.UserCreationRequest;
import org.example.identityservice.dto.response.UserCreationResponse;

public interface UserService {
    UserCreationResponse signUp(UserCreationRequest request, boolean registerByGoogleOAuth2);
}
