package org.example.paymentservice.repository;

import org.example.paymentservice.dto.response.PaymentMethodDetail;
import org.example.paymentservice.entity.PaymentMethod;

import java.util.List;

public interface PaymentMethodRepository {
    int create(PaymentMethod paymentMethod);

    List<PaymentMethodDetail> getAll() throws Exception;

    int editActive(String id, boolean active, long modifiedAt);
}
