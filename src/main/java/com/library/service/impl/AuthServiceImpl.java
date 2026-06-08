package com.library.service.impl;

import com.library.dto.request.LoginRequest;
import com.library.dto.request.RegisterRequest;
import com.library.exception.auth.AuthUnauthorizedException;
import com.library.model.User;
import com.library.model.UserRole;
import com.library.model.UserStatus;
import com.library.repository.UserRepository;
import com.library.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(RegisterRequest request) {
        log.info("Register user");

        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .passwordHash(request.getPassword())
                .role(UserRole.READER)
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(user);

        log.info("User registered successfully");
    }

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("User not found with email={}", request.getEmail());
                    return new AuthUnauthorizedException("Invalid email or password");
                });

        if (!user.getPasswordHash().equals(request.getPassword())) {
            throw new AuthUnauthorizedException("Invalid email or password");
        }

        log.info("User logged in successfully");
        return UUID.randomUUID().toString();
    }
}
