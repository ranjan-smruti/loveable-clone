package com.project.loveable_clone.controller;

import com.project.loveable_clone.dto.auth.AuthResponse;
import com.project.loveable_clone.dto.auth.LoginRequest;
import com.project.loveable_clone.dto.auth.SignupRequest;
import com.project.loveable_clone.dto.auth.UserProfileResponse;
import com.project.loveable_clone.service.AuthService;
import com.project.loveable_clone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request)
    {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile()
    {
        Long userId = 1L;
        return ResponseEntity.ok(userService.getProfile(userId));
    }

}
