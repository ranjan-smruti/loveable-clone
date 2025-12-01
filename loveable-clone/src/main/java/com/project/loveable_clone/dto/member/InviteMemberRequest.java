package com.project.loveable_clone.dto.member;

import com.project.loveable_clone.enums.ProjectMemberRole;

public record InviteMemberRequest(String email, ProjectMemberRole role) {
}
