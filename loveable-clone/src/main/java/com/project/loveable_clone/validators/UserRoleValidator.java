package com.project.loveable_clone.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRoleValidator implements ConstraintValidator<ValidUserRole, String> {

    private Set<String> allowedRoles;

    @Override
    public void initialize(ValidUserRole constraintAnnotation) {
        allowedRoles = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()) {
            return true;
        }

        boolean valid = allowedRoles.contains(value.toUpperCase());

        if(!valid)
        {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Invalid role '" + value + "'. Allowed values: " + allowedRoles
            ).addConstraintViolation();
        }

        return valid;
    }
}
