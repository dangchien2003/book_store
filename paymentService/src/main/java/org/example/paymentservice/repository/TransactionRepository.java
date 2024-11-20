package org.example.paymentservice.repository;

import org.example.paymentservice.entity.Transaction;
import org.example.paymentservice.enums.TransactionStatus;

public interface TransactionRepository {
    int create(Transaction transaction);

    int updateStatus(String orderId, TransactionStatus status);
}
