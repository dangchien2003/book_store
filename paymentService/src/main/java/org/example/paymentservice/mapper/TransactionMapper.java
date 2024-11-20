package org.example.paymentservice.mapper;

import org.example.paymentservice.dto.request.CreateTransactionRequest;
import org.example.paymentservice.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(source = "method", target = "paymentMethod")
    Transaction toTransaction(CreateTransactionRequest request);
}
