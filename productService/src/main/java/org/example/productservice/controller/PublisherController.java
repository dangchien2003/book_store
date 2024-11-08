package org.example.productservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productservice.dto.request.PublisherCreationRequest;
import org.example.productservice.dto.request.PublisherUpdateRequest;
import org.example.productservice.dto.response.ApiResponse;
import org.example.productservice.dto.response.PublisherResponse;
import org.example.productservice.service.PublisherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("publisher")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PublisherController {
    PublisherService publisherService;

    @PostMapping("add")
    ApiResponse<PublisherResponse> create(@RequestBody PublisherCreationRequest request) {
        return ApiResponse.<PublisherResponse>builder()
                .result(publisherService.create(request))
                .build();
    }

    @PutMapping("update")
    ApiResponse<PublisherResponse> update(@RequestBody PublisherUpdateRequest request) {
        return ApiResponse.<PublisherResponse>builder()
                .result(publisherService.update(request))
                .build();
    }

    @GetMapping("get")
    ApiResponse<PublisherResponse> get(@RequestParam("id") Integer id) {
        return ApiResponse.<PublisherResponse>builder()
                .result(publisherService.get(id))
                .build();
    }

    @GetMapping("/all")
    ApiResponse<List<PublisherResponse>> getAll(
            @Valid @Min(value = 1, message = "INVALID_PAGE_NUMBER")
            @RequestParam(value = "page", defaultValue = "1") int page) {
        return ApiResponse.<List<PublisherResponse>>builder()
                .result(publisherService.getAll(page))
                .build();
    }

    @GetMapping("/find")
    ApiResponse<List<PublisherResponse>> find(
            @Valid @Min(value = 1, message = "INVALID_PAGE_NUMBER")
            @RequestParam(value = "page", defaultValue = "1") int page,
            @Valid @NotBlank(message = "INVALID_NAME")
            @RequestParam(value = "name") String name
    ) {
        return ApiResponse.<List<PublisherResponse>>builder()
                .result(publisherService.getByName(name, page))
                .build();
    }

}
