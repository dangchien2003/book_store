package org.example.identityservice.utils;

import java.time.Instant;

public class TimeUtils {
    private TimeUtils() {
    }

    public static long getTimeStamp() {
        return Instant.now().toEpochMilli();
    }
}
