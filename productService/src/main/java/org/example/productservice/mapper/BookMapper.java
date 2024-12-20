package org.example.productservice.mapper;

import org.example.productservice.dto.request.BookCreationRequest;
import org.example.productservice.dto.request.BookUpdateRequest;
import org.example.productservice.dto.response.DetailInternal;
import org.example.productservice.dto.response.ManagerBookDetailResponse;
import org.example.productservice.dto.response.QuantityBookAfterMinusResponse;
import org.example.productservice.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "size", ignore = true)
    @Mapping(target = "statusCode", ignore = true)
    Book toBook(BookCreationRequest request);

    Book toBook(BookUpdateRequest request);

    @Mapping(target = "bookId", source = "id")
    QuantityBookAfterMinusResponse toQuantityBookAfterMinusResponse(DetailInternal detailInternal);

    ManagerBookDetailResponse toManagerBookDetailResponse(Book book);
}
