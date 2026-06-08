package com.library.exception.author;

import com.library.exception.NotFoundException;

public class AuthorNotFoundException extends NotFoundException {
    public AuthorNotFoundException(int id) {
        super("Author with id " + id + " not found");
    }
}
