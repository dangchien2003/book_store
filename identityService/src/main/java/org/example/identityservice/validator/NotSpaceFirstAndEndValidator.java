package org.example.identityservice.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotSpaceFirstAndEndValidator implements ConstraintValidator<NotSpaceFirstAndEnd, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.trim().length() == value.length();
    }
}
