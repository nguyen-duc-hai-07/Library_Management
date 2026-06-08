package com.library.exception.user;

import com.library.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(int id) {
        super("User with id " + id + " not found");
    }
}
