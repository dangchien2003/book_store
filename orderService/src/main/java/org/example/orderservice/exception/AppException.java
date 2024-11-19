package org.example.orderservice.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppException extends RuntimeException {
    final ErrorCode errorCode;
    String message;
    Object[] params;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AppException(ErrorCode errorCode, Object... params) {
        super(errorCode.getMessage());
        this.params = params;
        this.message = formatMessage(errorCode.getMessage(), params);
        this.errorCode = errorCode;
    }

    String formatMessage(String message, Object... params) {
        if (params == null || params.length == 0) {
            return message;
        }

        String formattedMessage = message;
        for (Object param : params) {
            if (formattedMessage.contains("?")) {
                formattedMessage = formattedMessage.replaceFirst("\\?", param != null ? param.toString() : "null");
            } else {
                break;
            }
        }
        return formattedMessage;
    }
}
