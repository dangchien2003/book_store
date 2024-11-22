package org.example.orderservice.mapper;

import org.example.orderservice.dto.response.BookDetailInternal;
import org.example.orderservice.entity.OrderDetail;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    @Mapping(target = "bookId", source = "id")
    @Mapping(target = "orderId", expression = "java(orderId)")
    @Mapping(target = "quantity", expression = "java(quantity)")
    OrderDetail toOrderDetail(BookDetailInternal bookDetail, @Context String orderId, @Context int quantity);
}
