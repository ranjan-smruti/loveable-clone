package com.project.loveable_clone.service.interfaces;

import com.project.loveable_clone.dto.auth.UserProfileResponse;

public interface UserService {
    UserProfileResponse getProfile(Long userId);
}
