package com.project.loveable_clone.dto.auth;

public record UserProfileResponse(
        Long id,
        String username,
        String email
)
{}
