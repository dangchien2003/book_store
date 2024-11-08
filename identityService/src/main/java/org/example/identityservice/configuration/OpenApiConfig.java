package org.example.identityservice.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Identity Service",
                version = "1.0",
                description = "This is the API for authentication"
        )
)
public class OpenApiConfig {
}
