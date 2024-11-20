package org.example.paymentservice.service;

import java.io.UnsupportedEncodingException;

public interface VnpayService {
    String generateUrl(String orderId, int amount) throws UnsupportedEncodingException;
}
