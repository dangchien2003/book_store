package org.example.identityservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.identityservice.dto.request.UserCreationRequest;
import org.example.identityservice.dto.response.UserCreationResponse;
import org.example.identityservice.entity.Role;
import org.example.identityservice.entity.User;
import org.example.identityservice.enums.UserStatus;
import org.example.identityservice.exception.AppException;
import org.example.identityservice.exception.ErrorCode;
import org.example.identityservice.mapper.UserMapper;
import org.example.identityservice.repository.RoleRepository;
import org.example.identityservice.repository.UserRepository;
import org.example.identityservice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    UserMapper usersMapper;

    public UserCreationResponse signUp(UserCreationRequest request, boolean registerByGoogleOAuth2) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (!Objects.isNull(user))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        user = usersMapper.toUser(request);

        Role role = roleRepository.findById("CUSTOMER")
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOTFOUND));

        user.setRole(role);
        saveUser(user, registerByGoogleOAuth2);
        return usersMapper.toUserCreationResponse(user);
    }

    void saveUser(User user, boolean registerByGoogleOAuth2) {
        user.setUid(UUID.randomUUID().toString());
        user.setStatusCode(registerByGoogleOAuth2 ? UserStatus.ACTIVE : UserStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
    }
}
