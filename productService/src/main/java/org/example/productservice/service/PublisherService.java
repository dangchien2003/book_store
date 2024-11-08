package org.example.productservice.service;

import org.example.productservice.dto.request.PublisherCreationRequest;
import org.example.productservice.dto.request.PublisherUpdateRequest;
import org.example.productservice.dto.response.PublisherResponse;

import java.util.List;

public interface PublisherService {
    PublisherResponse create(PublisherCreationRequest request);

    PublisherResponse update(PublisherUpdateRequest request);

    PublisherResponse get(Integer id);

    List<PublisherResponse> getAll(int pageNumber);

    List<PublisherResponse> getByName(String name, int pageNumber);
}
