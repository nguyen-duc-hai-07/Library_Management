package com.library.exception.book;

import com.library.exception.BadRequestException;

public class BookBadRequestException extends BadRequestException {
    public BookBadRequestException(String message) {
        super(message);
    }
}
