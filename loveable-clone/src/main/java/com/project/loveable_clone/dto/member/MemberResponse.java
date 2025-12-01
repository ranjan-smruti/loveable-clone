package com.project.loveable_clone.dto.member;

import com.project.loveable_clone.enums.ProjectMemberRole;

import java.time.Instant;

public record MemberResponse(Long userId,
                             String email,
                             String name,
                             String avatarUrl,
                             ProjectMemberRole role,
                             Instant invitedAt) {
}
