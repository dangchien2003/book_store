package org.example.paymentservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.exception.ErrorCode;
import org.example.paymentservice.configuration.ScheduleRunner;
import org.example.paymentservice.dto.request.CreateTransactionRequest;
import org.example.paymentservice.dto.response.DataBankingQueue;
import org.example.paymentservice.dto.response.TransactionCreationResponse;
import org.example.paymentservice.entity.Transaction;
import org.example.paymentservice.enums.PaymentMethodType;
import org.example.paymentservice.enums.TransactionStatus;
import org.example.paymentservice.exception.AppException;
import org.example.paymentservice.mapper.TransactionMapper;
import org.example.paymentservice.repository.TransactionRepository;
import org.example.paymentservice.service.TransactionService;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;
    TransactionMapper transactionMapper;

    @Override
    public TransactionCreationResponse create(CreateTransactionRequest request) {
        Transaction transaction = transactionMapper.toTransaction(request);
        transaction.setStatus(TransactionStatus.CREATED.name());
        transaction.onCreate();

        if (transactionRepository.create(transaction) == 0)
            throw new AppException(ErrorCode.UPDATE_FAIL);

        PaymentMethodType method = EnumUtils.findEnumInsensitiveCase(PaymentMethodType.class, transaction.getPaymentMethod());

        switch (method) {
            case PAYPAL -> {
                ScheduleRunner.waitForPaymentPaypal.put(transaction.getOrderId(), transaction.getAmount());
                return TransactionCreationResponse.builder()
                        .amount(transaction.getAmount())
                        .canceledAfter(3600)
                        .code(transaction.getOrderId())
                        .redirect("link")
                        .build();
            }
            case BANKING -> addScheduleBanking(transaction.getOrderId(), transaction.getAmount(), 1, ChronoUnit.HOURS);
        }
        return TransactionCreationResponse.builder()
                .amount(transaction.getAmount())
                .canceledAfter(3600)
                .code(transaction.getOrderId())
                .build();
    }

    void addScheduleBanking(String orderId, int amount, int removeAfter, ChronoUnit unit) {
        long removeAt = Instant.now().plus(removeAfter, unit).toEpochMilli();
        DataBankingQueue dataBankingQueue = DataBankingQueue.builder()
                .amount(amount)
                .removeAt(removeAt)
                .build();

        ScheduleRunner.waitForPaymentBanking.put(orderId, dataBankingQueue);
    }

}
