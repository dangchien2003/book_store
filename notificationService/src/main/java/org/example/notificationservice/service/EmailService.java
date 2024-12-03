package org.example.notificationservice.service;

import org.example.event.CreateAccountSuccess;

public interface EmailService {
    void createAccountSuccess(CreateAccountSuccess data);
}
