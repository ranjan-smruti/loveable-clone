package com.project.loveable_clone.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserRoleValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserRole {
    Class<? extends Enum<?>> enumClass();
    String message() default "Invalid user role";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
