package com.project.loveable_clone.dto.member;

import com.project.loveable_clone.enums.ProjectMemberRole;
import com.project.loveable_clone.validators.ValidEmail;
import com.project.loveable_clone.validators.ValidUserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InviteMemberRequest(
        @NotBlank @ValidEmail String email,
        @NotNull @ValidUserRole(enumClass = ProjectMemberRole.class) ProjectMemberRole role) {
}
