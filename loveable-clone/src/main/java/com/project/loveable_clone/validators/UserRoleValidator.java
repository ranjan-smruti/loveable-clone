package com.project.loveable_clone.validators;

import com.project.loveable_clone.enums.ProjectMemberRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public class UserRoleValidator implements ConstraintValidator<ValidUserRole, ProjectMemberRole> {

    private Set<ProjectMemberRole> allowedRoles;

    @Override
    public void initialize(ValidUserRole constraintAnnotation) {
        allowedRoles = EnumSet.allOf(ProjectMemberRole.class);
    }

    @Override
    public boolean isValid(ProjectMemberRole value,
                           ConstraintValidatorContext context) {

        if (value == null) {
            return true; // @NotNull handles null
        }

        if (!allowedRoles.contains(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Invalid role '" + value +
                            "'. Allowed values: " + allowedRoles
            ).addConstraintViolation();
            return false;
        }

        return true;
    }
}
