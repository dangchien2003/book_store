package org.example.identityservice.mapper;

import org.example.identityservice.dto.response.RoleEditPermissionResponse;
import org.example.identityservice.entity.Permission;
import org.example.identityservice.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class MapRole {
    public RoleEditPermissionResponse toRoleEditPermissionResponse(Role role) {
        String[] permissionNames = role.getPermissions().stream()
                .map(Permission::getName)
                .toArray((String[]::new));

        return RoleEditPermissionResponse.builder()
                .roleName(role.getName())
                .permissions(permissionNames)
                .build();
    }

}
