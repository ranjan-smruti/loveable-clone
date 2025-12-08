package com.project.loveable_clone.service.implementations;

import com.project.loveable_clone.dto.auth.AuthResponse;
import com.project.loveable_clone.dto.auth.LoginRequest;
import com.project.loveable_clone.dto.auth.SignupRequest;
import com.project.loveable_clone.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceClass implements AuthService {
    @Override
    public AuthResponse signup(SignupRequest request) {
        return null;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }
}
