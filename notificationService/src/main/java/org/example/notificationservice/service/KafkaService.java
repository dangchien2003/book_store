package org.example.notificationservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.event.CreateAccountSuccess;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class KafkaService {
    EmailService emailService;

    @KafkaListener(topics = "create-account-success")
    public void createAccountSuccess(CreateAccountSuccess message) {
        emailService.createAccountSuccess(message);
    }
}
