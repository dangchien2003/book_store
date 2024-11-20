package org.example.paymentservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.exception.ErrorCode;
import org.example.paymentservice.dto.request.CreatePaymentMethodRequest;
import org.example.paymentservice.dto.request.EditActiveForPaymentMethodRequest;
import org.example.paymentservice.dto.response.PaymentMethodDetail;
import org.example.paymentservice.entity.PaymentMethod;
import org.example.paymentservice.exception.AppException;
import org.example.paymentservice.mapper.PaymentMethodMapper;
import org.example.paymentservice.repository.PaymentMethodRepository;
import org.example.paymentservice.service.PaymentMethodService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentMethodServiceImpl implements PaymentMethodService {
    PaymentMethodMapper paymentMethodMapper;
    PaymentMethodRepository paymentMethodRepository;

    @Override
    public void create(CreatePaymentMethodRequest request) {
        PaymentMethod paymentMethod = paymentMethodMapper.toPaymentMethod(request);
        byte defaultActive = 0;
        paymentMethod.setActive(defaultActive);
        paymentMethod.onCreate();

        if (paymentMethodRepository.create(paymentMethod) == 0)
            throw new AppException(ErrorCode.UPDATE_FAIL);
    }

    @Override
    public List<PaymentMethodDetail> getAll() {
        try {
            return paymentMethodRepository.getAll();
        } catch (Exception e) {
            log.error("paymentMethodRepository.getAll error: ", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public void updateActive(String id, EditActiveForPaymentMethodRequest request) {
        paymentMethodRepository.editActive(id, request.getActive());
    }
}
