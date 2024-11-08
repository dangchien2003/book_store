package org.example.productservice.service;

import org.example.productservice.dto.request.AuthorCreationRequest;
import org.example.productservice.dto.request.AuthorUpdateRequest;
import org.example.productservice.dto.response.AuthorResponse;

import java.util.List;

public interface AuthorService {
    AuthorResponse create(AuthorCreationRequest request);

    AuthorResponse update(AuthorUpdateRequest request);

    AuthorResponse get(Long id);

    List<AuthorResponse> getAll(int pageNumber);

    List<AuthorResponse> getByName(String name, int pageNumber);
}
