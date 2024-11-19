package org.example.orderservice.util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.ThreadLocalRandom;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RandomUtils {
    public final static String PATTERN1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomString(int length, String pattern) {
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(pattern.length());
            randomString.append(pattern.charAt(randomIndex));
        }

        return randomString.toString();
    }
}
