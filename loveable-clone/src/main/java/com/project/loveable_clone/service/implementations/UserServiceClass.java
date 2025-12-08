package com.project.loveable_clone.service.implementations;

import com.project.loveable_clone.dto.auth.UserProfileResponse;
import com.project.loveable_clone.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceClass implements UserService {
    @Override
    public UserProfileResponse getProfile(Long userId) {
        return null;
    }
}
