package com.library.service;

import com.library.dto.request.LoginRequest;
import com.library.dto.request.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request) ;

    String login(LoginRequest request) ;
}
