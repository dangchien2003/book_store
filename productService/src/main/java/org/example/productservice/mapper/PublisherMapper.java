package org.example.productservice.mapper;

import org.example.productservice.dto.request.PublisherCreationRequest;
import org.example.productservice.dto.request.PublisherUpdateRequest;
import org.example.productservice.dto.response.PublisherResponse;
import org.example.productservice.entity.Publisher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    Publisher toPublisher(PublisherCreationRequest request);

    Publisher toPublisher(PublisherUpdateRequest request);

    PublisherResponse toPublisherResponse(Publisher publisher);

}
