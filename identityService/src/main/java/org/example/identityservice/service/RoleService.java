package org.example.identityservice.service;

import org.example.identityservice.dto.request.RoleCreationRequest;
import org.example.identityservice.dto.request.RoleEditPermissionRequest;
import org.example.identityservice.dto.response.RoleCreationResponse;
import org.example.identityservice.dto.response.RoleEditPermissionResponse;

public interface RoleService {
    RoleCreationResponse create(RoleCreationRequest request);

    RoleEditPermissionResponse editPermission(RoleEditPermissionRequest request);
}
