package com.project.loveable_clone.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class UserEmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {

        if(s == null || s.isBlank()){
            return true;
        }

        boolean valid = EMAIL_PATTERN.matcher(s).matches();

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Invalid userName address: " + s
            ).addConstraintViolation();
        }

        return valid;
    }
}
