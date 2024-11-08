package org.example.productservice.mapper;

import org.example.productservice.dto.request.AuthorCreationRequest;
import org.example.productservice.dto.request.AuthorUpdateRequest;
import org.example.productservice.dto.response.AuthorResponse;
import org.example.productservice.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toAuthor(AuthorCreationRequest request);

    Author toAuthor(AuthorUpdateRequest request);

    AuthorResponse toAuthorResponse(Author author);

}
