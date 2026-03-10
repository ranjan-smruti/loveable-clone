package com.project.loveable_clone.dto.project;

import com.project.loveable_clone.enums.ProjectMemberRole;

import java.time.Instant;

public record ProjectSummaryResponse(Long id,
                                     String name,
                                     Instant createdAt,
                                     Instant updatedAt,
                                     ProjectMemberRole role) {
}
