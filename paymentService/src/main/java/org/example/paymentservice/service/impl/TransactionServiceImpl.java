package org.example.paymentservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
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
import org.example.paymentservice.service.VnpayService;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;
    TransactionMapper transactionMapper;
    VnpayService vnpayService;

    @NonFinal
    int minutesDestroyScanForBanking = 60;

    @Override
    public TransactionCreationResponse create(CreateTransactionRequest request) {
        Transaction transaction = transactionMapper.toTransaction(request);
        transaction.setStatus(TransactionStatus.CREATED.name());
        transaction.onCreate();

        if (transactionRepository.create(transaction) == 0)
            throw new AppException(ErrorCode.UPDATE_FAIL);

        int countDown = 0;
        String urlRedirect = null;
        PaymentMethodType method = EnumUtils.findEnumInsensitiveCase(PaymentMethodType.class, transaction.getPaymentMethod());
        switch (method) {
            case PAYPAL -> ScheduleRunner.waitForPaymentPaypal.put(transaction.getOrderId(), transaction.getAmount());
            case BANKING -> {
                countDown = convertMinutesToSeconds(minutesDestroyScanForBanking);
                addScheduleForBanking(transaction.getOrderId(), transaction.getAmount(), countDown);
            }
            case VNPAY -> {
                try {
                    urlRedirect = vnpayService.generateUrl(request.getOrderId(), request.getAmount());
                } catch (UnsupportedEncodingException e) {
                    log.error("vnpayService.generateUrl error: ", e);
                    throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
                }
            }
            case COD -> log.info("cod");
        }

        if (transactionRepository.updateStatus(transaction.getOrderId(), TransactionStatus.PENDING) == 0)
            throw new AppException(ErrorCode.UPDATE_STATUS_FAIL);

        return TransactionCreationResponse.builder()
                .redirect(urlRedirect)
                .amount(transaction.getAmount())
                .canceledAfter(countDown)
                .code(transaction.getOrderId())
                .paymentMethod(method.name())
                .build();
    }

    void addScheduleForBanking(String orderId, int amount, int removeAfterSeconds) {
        long removeAt = Instant.now().plus(removeAfterSeconds, ChronoUnit.SECONDS).toEpochMilli();
        DataBankingQueue dataBankingQueue = DataBankingQueue.builder()
                .amount(amount)
                .removeAt(removeAt)
                .build();

        ScheduleRunner.waitForPaymentBanking.put(orderId, dataBankingQueue);
    }

    public static int convertMinutesToSeconds(int minute) {
        return minute * 60;
    }

}
