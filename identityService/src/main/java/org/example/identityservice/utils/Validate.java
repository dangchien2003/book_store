package org.example.identityservice.utils;

public class Validate {
    private Validate() {
    }

    public final static int minLengthPassword = 8;
    public final static int maxLengthPassword = 20;

    public static boolean isEmail(String value) {
        return value.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    }

    public static boolean isPhoneNumberVN(String value) {
        return value.matches("^[0-9]{10}$");
    }


}
