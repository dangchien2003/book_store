package org.example.identityservice.utils;

import java.security.SecureRandom;

public class RandomUtils {
    private RandomUtils() {
    }

    private static final int ASCII_START = 33;  // Ký tự ASCII đầu tiên (ký tự đặc biệt '!')
    private static final int ASCII_END = 126;   // Ký tự ASCII cuối cùng ('~')
    private static final SecureRandom RANDOM = new SecureRandom();

    private static String random(int length) {
        if (length <= 0) throw new IllegalArgumentException("Length must be greater than 0");

        StringBuilder randomString = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomAscii = ASCII_START + RANDOM.nextInt(ASCII_END - ASCII_START + 1);
            randomString.append((char) randomAscii);
        }
        return randomString.toString();
    }

    public static String randomPassword() {
        return random(10);
    }

}
