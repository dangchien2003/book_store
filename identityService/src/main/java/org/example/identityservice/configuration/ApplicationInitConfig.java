package org.example.identityservice.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.identityservice.entity.Role;
import org.example.identityservice.entity.User;
import org.example.identityservice.enums.UserStatus;
import org.example.identityservice.exception.AppException;
import org.example.identityservice.exception.ErrorCode;
import org.example.identityservice.repository.RoleRepository;
import org.example.identityservice.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@RequiredArgsConstructor
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            Role role = roleRepository.findById("ADMIN").orElseThrow(() ->
                    new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));

            if (userRepository.countByRole(role) == 0) {
                User user = User.builder()
                        .uid(UUID.randomUUID().toString())
                        .email("admin@gmail.com")
                        .name("ADMIN")
                        .password(passwordEncoder.encode("admin"))
                        .statusCode(UserStatus.ACTIVE)
                        .role(role)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been create");
            }
        };
    }
}
