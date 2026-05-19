package com.library.controller;

import com.library.dto.request.LoginRequest;
import com.library.dto.request.RegisterRequest;
import com.library.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request) throws Exception {
        log.info("POST /api/v1/auth/register");
        authService.register(request);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) throws Exception {
        log.info("POST /api/v1/auth/login");
        String token = authService.login(request);
        return Map.of("token", token);
    }
}
