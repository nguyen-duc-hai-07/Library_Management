package com.library.exception.auth;

import com.library.exception.UnauthorizedException;

public class AuthUnauthorizedException extends UnauthorizedException {
    public AuthUnauthorizedException(String message) {
        super(message);
    }
}
