package org.example.identityservice.mapper;

import org.example.identityservice.dto.request.PermissionCreationRequest;
import org.example.identityservice.dto.response.PermissionResponse;
import org.example.identityservice.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionCreationRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
