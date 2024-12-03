package org.example.identityservice.service;

import org.example.event.CreateAccountSuccess;

public interface KafkaService {
    void sendEmail(CreateAccountSuccess data);
}
