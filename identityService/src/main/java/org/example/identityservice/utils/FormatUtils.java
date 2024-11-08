package org.example.identityservice.utils;

public class FormatUtils {
    private FormatUtils() {
    }

    public static String replaceSpaceToUnderline(String value) {
        return value.replace(' ', '_');
    }

    public static boolean validateNamePermissionAndRole(String name) {
        return name.matches("^[A-Z0-9_]+$");
    }
}
