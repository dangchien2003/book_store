package org.example.paymentservice.configuration;

import org.example.paymentservice.dto.response.DataBankingQueue;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ScheduleRunner {
    public static Map<String, DataBankingQueue> waitForPaymentBanking = new HashMap<>();
    public static Map<String, Integer> waitForPaymentPaypal = new HashMap<>();
}
