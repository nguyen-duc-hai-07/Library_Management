package com.library.service;

import com.library.dto.request.LoginRequest;
import com.library.dto.request.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request) throws Exception;

    String login(LoginRequest request) throws Exception;
}
