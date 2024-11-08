package org.example.identityservice.service;

import org.example.identityservice.dto.request.PermissionCreationRequest;
import org.example.identityservice.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    List<PermissionResponse> create(List<PermissionCreationRequest> request);

    List<PermissionResponse> getAll(int pageNumber);
}
