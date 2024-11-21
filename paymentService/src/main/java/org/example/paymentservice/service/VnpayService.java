package org.example.paymentservice.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.paymentservice.dto.response.CheckTransactionResponse;

import java.io.UnsupportedEncodingException;

public interface VnpayService {
    String generateUrl(String orderId, int amount, String ipAddress) throws UnsupportedEncodingException;

    CheckTransactionResponse queryTransaction(HttpServletRequest request);
}
