package com.library.service.impl;

import com.library.dto.request.LoginRequest;
import com.library.dto.request.RegisterRequest;
import com.library.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Override
    public void register(RegisterRequest request) throws Exception {

    }

    @Override
    public String login(LoginRequest request) throws Exception {
        return "";
    }
}
