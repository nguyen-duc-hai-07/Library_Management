package com.library.exception.fine;

import com.library.exception.NotFoundException;

public class FineNotFoundException extends NotFoundException {
    public FineNotFoundException(int id) {
        super("Fine with id " + id + " not found");
    }
}
