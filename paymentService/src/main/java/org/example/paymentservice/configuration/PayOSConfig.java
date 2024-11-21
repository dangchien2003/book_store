package org.example.paymentservice.configuration;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.payos.PayOS;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PayOSConfig {
    @NonFinal
    @Value("${PAYOS_CLIENT_ID}")
    String clientId;

    @NonFinal
    @Value("${PAYOS_API_KEY}")
    String apiKey;

    @NonFinal
    @Value("${PAYOS_CHECKSUM_KEY}")
    String checksumKey;

    @Bean
    public PayOS payOS() {
        return new PayOS(clientId, apiKey, checksumKey);
    }
}
