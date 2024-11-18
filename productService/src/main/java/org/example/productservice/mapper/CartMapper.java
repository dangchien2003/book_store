package org.example.productservice.mapper;

import org.example.productservice.dto.request.AddCartItemRequest;
import org.example.productservice.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart toCart(String userId, AddCartItemRequest request);
}
