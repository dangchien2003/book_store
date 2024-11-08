package org.example.identityservice.utils;


import org.example.identityservice.exception.AppException;
import org.example.identityservice.exception.ErrorCode;

import java.util.Locale;

public class ENumUtils {
    private ENumUtils() {
    }

    public static <T extends Enum<T>> T getType(Class<T> enumType, String type) {
        type = type.toUpperCase(Locale.ROOT);

        try {
            return Enum.valueOf(enumType, type);
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_DATA);
        }
    }
}
