package org.example.identityservice.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public enum TokenStatus {
    REJECT(1),
    NON_REJECT(0),
    ;

    int status;

    public static TokenStatus fromStatus(int status) {
        if (status == 0) {
            return TokenStatus.NON_REJECT;
        } else {
            return TokenStatus.REJECT;
        }
    }
}
