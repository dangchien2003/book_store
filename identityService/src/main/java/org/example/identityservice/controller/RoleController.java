package org.example.identityservice.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.identityservice.dto.request.RoleCreationRequest;
import org.example.identityservice.dto.request.RoleEditPermissionRequest;
import org.example.identityservice.dto.response.ApiResponse;
import org.example.identityservice.dto.response.RoleCreationResponse;
import org.example.identityservice.dto.response.RoleEditPermissionResponse;
import org.example.identityservice.service.RoleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RoleController {
    RoleService roleService;

    @PostMapping("/add")
    ApiResponse<RoleCreationResponse> createRole(@Valid @RequestBody RoleCreationRequest request) {
        return ApiResponse.<RoleCreationResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @PutMapping("/edit-permission")
    ApiResponse<RoleEditPermissionResponse> editPermission(@Valid @RequestBody RoleEditPermissionRequest request) {
        return ApiResponse.<RoleEditPermissionResponse>builder()
                .result(roleService.editPermission(request))
                .build();
    }
}
