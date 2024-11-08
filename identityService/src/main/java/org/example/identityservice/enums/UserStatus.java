package org.example.identityservice.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum UserStatus {
    ACTIVE,
    BLOCK,
    PENDING_VERIFIER
}
