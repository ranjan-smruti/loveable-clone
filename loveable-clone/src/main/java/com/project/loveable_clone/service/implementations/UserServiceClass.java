package com.project.loveable_clone.service.implementations;

import com.project.loveable_clone.advice.exceptions.UsernameNotFoundException;
import com.project.loveable_clone.dto.auth.UserProfileResponse;
import com.project.loveable_clone.repository.UserRepository;
import com.project.loveable_clone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceClass implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User", username));
    }

    @Override
    public UserProfileResponse getProfile(Long userId) {
        return null;
    }
}
