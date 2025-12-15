package com.project.loveable_clone.dto.member;

import com.project.loveable_clone.enums.ProjectMemberRole;
import com.project.loveable_clone.validators.ValidUserRole;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberRoleRequest(
        @NotNull @ValidUserRole(enumClass = ProjectMemberRole.class) ProjectMemberRole role) {
}
