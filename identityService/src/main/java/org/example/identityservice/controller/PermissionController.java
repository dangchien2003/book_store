package org.example.identityservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.identityservice.dto.request.PermissionCreationRequest;
import org.example.identityservice.dto.response.ApiResponse;
import org.example.identityservice.dto.response.PermissionResponse;
import org.example.identityservice.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping("add")
    ApiResponse<List<PermissionResponse>> creation(@Valid @RequestBody List<PermissionCreationRequest> request) {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping("all")
    ApiResponse<List<PermissionResponse>> getAll(
            @Valid @Min(value = 1, message = "INVALID_PAGE_NUMBER") @RequestParam(name = "page", defaultValue = "1") int pageNumber) {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll(pageNumber))
                .build();
    }
}
