package com.library.exception.fine;

import com.library.exception.BadRequestException;

public class FineBadRequestException extends BadRequestException {
    public FineBadRequestException(String message) {
        super(message);
    }
}
