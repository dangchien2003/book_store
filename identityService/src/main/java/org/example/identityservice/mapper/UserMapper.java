package org.example.identityservice.mapper;

import org.example.identityservice.dto.request.UserCreationRequest;
import org.example.identityservice.dto.response.UserCreationResponse;
import org.example.identityservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserCreationResponse toUserCreationResponse(User user);
}
