package org.example.identityservice.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.identityservice.entity.Token;
import org.example.identityservice.repository.TokenRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TokenTask {
    TokenRepository tokenRepository;

    public static final List<Token> TOKEN_SAVE = Collections.synchronizedList(new LinkedList<>());

    @Scheduled(cron = "*/5 * * * * *")
    public void taskUpdateProduct() {

        if (TOKEN_SAVE.isEmpty()) {
            return;
        }

        List<Token> tokens = new ArrayList<>(TOKEN_SAVE).subList(0, TOKEN_SAVE.size());
        TOKEN_SAVE.removeAll(tokens);

        tokenRepository.saveAll(tokens);
    }
}
