package org.example.productservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadConfig {
    @Bean
    ExecutorService initExecutors() {
        return Executors.newFixedThreadPool(8);
    }
}
