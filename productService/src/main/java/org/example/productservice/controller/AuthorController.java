package org.example.productservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productservice.dto.request.AuthorCreationRequest;
import org.example.productservice.dto.request.AuthorUpdateRequest;
import org.example.productservice.dto.response.ApiResponse;
import org.example.productservice.dto.response.AuthorResponse;
import org.example.productservice.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("author")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthorController {
    AuthorService authorService;

    @PostMapping("add")
    ApiResponse<AuthorResponse> create(@RequestBody AuthorCreationRequest request) {
        return ApiResponse.<AuthorResponse>builder()
                .result(authorService.create(request))
                .build();
    }

    @PutMapping("update")
    ApiResponse<AuthorResponse> update(@RequestBody AuthorUpdateRequest request) {
        return ApiResponse.<AuthorResponse>builder()
                .result(authorService.update(request))
                .build();
    }

    @GetMapping("get")
    ApiResponse<AuthorResponse> get(@RequestParam("id") Long id) {
        return ApiResponse.<AuthorResponse>builder()
                .result(authorService.get(id))
                .build();
    }

    @GetMapping("/all")
    ApiResponse<List<AuthorResponse>> getAll(
            @Valid @Min(value = 1, message = "INVALID_PAGE_NUMBER")
            @RequestParam(value = "page", defaultValue = "1") int page) {
        return ApiResponse.<List<AuthorResponse>>builder()
                .result(authorService.getAll(page))
                .build();
    }

    @GetMapping("/find")
    ApiResponse<List<AuthorResponse>> find(
            @Valid @Min(value = 1, message = "INVALID_PAGE_NUMBER")
            @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @NotBlank(message = "INVALID_NAME")
            @RequestParam(value = "name") String name
    ) {
        return ApiResponse.<List<AuthorResponse>>builder()
                .result(authorService.getByName(name, page))
                .build();
    }

}
