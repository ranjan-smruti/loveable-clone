package com.project.loveable_clone.dto.auth;

import com.project.loveable_clone.validators.ValidEmail;
import com.project.loveable_clone.validators.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank @ValidEmail String username,
        @Size(min=1, max=30) String name,
        @NotBlank @Size(min=4, max=8) @ValidPassword String password) {
}
