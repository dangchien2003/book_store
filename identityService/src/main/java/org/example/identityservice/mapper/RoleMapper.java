package org.example.identityservice.mapper;

import org.example.identityservice.dto.request.RoleCreationRequest;
import org.example.identityservice.dto.response.RoleCreationResponse;
import org.example.identityservice.dto.response.RoleEditPermissionResponse;
import org.example.identityservice.entity.Permission;
import org.example.identityservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleCreationRequest request);

    RoleCreationResponse toRoleCreationResponse(Role role);

    default RoleEditPermissionResponse toRoleEditPermissionResponse(Role role) {
        String[] permissionNames = role.getPermissions().stream()
                .map(Permission::getName)
                .toArray((String[]::new));

        return RoleEditPermissionResponse.builder()
                .roleName(role.getName())
                .permissions(permissionNames)
                .build();
    }
}
