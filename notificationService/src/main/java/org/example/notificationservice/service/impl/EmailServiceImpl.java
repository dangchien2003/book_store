package org.example.notificationservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.event.CreateAccountSuccess;
import org.example.notificationservice.service.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailServiceImpl implements EmailService {
    JavaMailSender emailSender;
    TemplateEngine templateEngine;
    ObjectMapper objectMapper;

    @Override
    public void createAccountSuccess(CreateAccountSuccess data) {
        String template = "create-account-success";

        String body = getTemplate(template, data);
        if (!sendMail(new String[]{data.getEmail()}, "Chúc mừng bạn đã đăng ký thành công tài khoản Book Store", body)) {
            log.error("Send email createAccountSuccess fail for: " + data.getEmail());
        }
    }

    boolean sendMail(String[] to, String subject, String body) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            emailSender.send(message);
        } catch (Exception e) {
            log.error("Lỗi gửi mail: ", e);
            return false;
        }

        return true;
    }

    String getTemplate(String templateName, Object data) {
        Context context = new Context();
        try {
            context.setVariables(objectMapper.convertValue(data, Map.class));
        } catch (IllegalArgumentException e) {
            log.error("Lỗi convert object to map");
            return null;
        }
        return templateEngine.process(templateName, context);
    }


}
