package org.example.paymentservice.mapper;

import org.example.paymentservice.dto.request.CreatePaymentMethodRequest;
import org.example.paymentservice.entity.PaymentMethod;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    PaymentMethod toPaymentMethod(CreatePaymentMethodRequest request);
}
