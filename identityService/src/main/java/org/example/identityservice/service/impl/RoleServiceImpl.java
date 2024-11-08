package org.example.identityservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.identityservice.dto.request.RoleCreationRequest;
import org.example.identityservice.dto.request.RoleEditPermissionRequest;
import org.example.identityservice.dto.response.RoleCreationResponse;
import org.example.identityservice.dto.response.RoleEditPermissionResponse;
import org.example.identityservice.entity.Permission;
import org.example.identityservice.entity.Role;
import org.example.identityservice.exception.AppException;
import org.example.identityservice.exception.ErrorCode;
import org.example.identityservice.mapper.RoleMapper;
import org.example.identityservice.repository.PermissionRepository;
import org.example.identityservice.repository.RoleRepository;
import org.example.identityservice.service.RoleService;
import org.example.identityservice.utils.FormatUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleCreationResponse create(RoleCreationRequest request) {

        request.setName(
                FormatUtils.replaceSpaceToUnderline(request.getName()));

        if (!FormatUtils.validateNamePermissionAndRole(request.getName()))
            throw new AppException(ErrorCode.INVALID_NAME);

        Role role = roleRepository.findById(request.getName())
                .orElse(null);

        if (!Objects.isNull(role))
            throw new AppException(ErrorCode.ROLE_EXISTED);

        role = roleMapper.toRole(request);

        role = roleRepository.save(role);

        return roleMapper.toRoleCreationResponse(role);
    }

    public RoleEditPermissionResponse editPermission(RoleEditPermissionRequest request) {
        Role role = roleRepository.findById(request.getRoleName().toUpperCase(Locale.ROOT))
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOTFOUND));

        List<Permission> permissions = permissionRepository
                .findAllById(new ArrayList<>(request.getPermissions()));

        if (permissions.size() != request.getPermissions().size())
            throw new AppException(ErrorCode.PERMISSION_NOTFOUND);

        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);

        return roleMapper.toRoleEditPermissionResponse(role);
    }
}
