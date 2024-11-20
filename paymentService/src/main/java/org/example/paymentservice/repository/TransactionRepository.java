package org.example.paymentservice.repository;

import org.example.paymentservice.entity.Transaction;

public interface TransactionRepository {
    int create(Transaction transaction);
}
