package org.example.paymentservice.service;

import org.example.paymentservice.dto.request.CreateTransactionRequest;
import org.example.paymentservice.dto.response.TransactionCreationResponse;

public interface TransactionService {
    TransactionCreationResponse create(CreateTransactionRequest request);
}
