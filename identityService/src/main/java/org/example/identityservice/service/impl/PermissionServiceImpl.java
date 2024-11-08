package org.example.identityservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.identityservice.dto.request.PermissionCreationRequest;
import org.example.identityservice.dto.response.PermissionResponse;
import org.example.identityservice.entity.Permission;
import org.example.identityservice.exception.AppException;
import org.example.identityservice.exception.ErrorCode;
import org.example.identityservice.mapper.PermissionMapper;
import org.example.identityservice.repository.PermissionRepository;
import org.example.identityservice.service.PermissionService;
import org.example.identityservice.utils.FormatUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Transactional
    public List<PermissionResponse> create(List<PermissionCreationRequest> request) {
        List<Permission> entityPermissions = new ArrayList<>();
        List<String> permissionNames = new ArrayList<>();
        for (PermissionCreationRequest permissionCreation : request) {
            permissionCreation.setName(permissionCreation.getName().toUpperCase());

            permissionCreation.setName(
                    FormatUtils.replaceSpaceToUnderline(permissionCreation.getName()));

            if (!FormatUtils.validateNamePermissionAndRole(permissionCreation.getName()))
                throw new AppException(ErrorCode.INVALID_NAME);

            entityPermissions.add(permissionMapper.toPermission(permissionCreation));
            permissionNames.add(permissionCreation.getName());
        }

        if (permissionRepository.countByNames(permissionNames) > 0)
            throw new AppException(ErrorCode.PERMISSION_EXISTED);

        entityPermissions = permissionRepository.saveAll(entityPermissions);
        return entityPermissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    //    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<PermissionResponse> getAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 2);
        return permissionRepository.findAll(pageable).stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }
//
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
//    public void delete(String namePermission) {
//        try {
//            permissionRepository.deleteById(namePermission);
//        } catch (DataIntegrityViolationException e) {
//            throw new AppException(ErrorCode.CANNOT_DELETE);
//        }
//    }
}
