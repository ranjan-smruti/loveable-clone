package com.project.loveable_clone.service.implementations;

import com.project.loveable_clone.advice.exceptions.BadRequestException;
import com.project.loveable_clone.dto.auth.AuthResponse;
import com.project.loveable_clone.dto.auth.LoginRequest;
import com.project.loveable_clone.dto.auth.SignupRequest;
import com.project.loveable_clone.entity.UserEntity;
import com.project.loveable_clone.mappers.UserMapper;
import com.project.loveable_clone.repository.UserRepository;
import com.project.loveable_clone.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceClass implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public AuthResponse signup(SignupRequest request) {
        userRepository.findByUsername(request.username()).ifPresent(user -> {
            throw new BadRequestException("User already exists with username: " + request.username());
        });

        UserEntity user = userMapper.toUserEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        return new AuthResponse("dummy-token", userMapper.toUserProfileResponse(user));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }
}
