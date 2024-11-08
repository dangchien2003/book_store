package org.example.identityservice.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.example.identityservice.utils.Validate;

@Slf4j
public class IsEmailValidator implements ConstraintValidator<IsEmail, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.info(value);
        return Validate.isEmail(value);
    }

    @Override
    public void initialize(IsEmail constraintAnnotation) {
    }
}
