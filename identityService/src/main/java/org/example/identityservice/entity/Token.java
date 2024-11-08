package org.example.identityservice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.identityservice.enums.TokenStatus;
import org.example.identityservice.enums.TokenType;

@Entity
@Builder
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {
    @Id
    String tokenId;
    long expireAt;
    @Enumerated(EnumType.STRING)
    TokenType type;
    @Column(nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 0")
    TokenStatus reject;

    public Token() {
        this.reject = TokenStatus.NON_REJECT;
    }
}
