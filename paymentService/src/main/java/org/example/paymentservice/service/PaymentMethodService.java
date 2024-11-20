package org.example.paymentservice.service;

import org.example.paymentservice.dto.request.CreatePaymentMethodRequest;
import org.example.paymentservice.dto.request.EditActiveForPaymentMethodRequest;
import org.example.paymentservice.dto.response.PaymentMethodDetail;

import java.util.List;

public interface PaymentMethodService {
    void create(CreatePaymentMethodRequest request);

    List<PaymentMethodDetail> getAll();

    void updateActive(String id, EditActiveForPaymentMethodRequest request);
}
