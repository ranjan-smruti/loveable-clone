package com.project.loveable_clone.dto.auth;

import com.project.loveable_clone.validators.ValidEmail;
import com.project.loveable_clone.validators.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest (
        @NotBlank @ValidEmail String username,
        @NotBlank @Size(min=4, max=8) @ValidPassword String password){
}
