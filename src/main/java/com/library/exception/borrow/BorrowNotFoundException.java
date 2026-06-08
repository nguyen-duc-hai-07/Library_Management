package com.library.exception.borrow;

import com.library.exception.NotFoundException;

public class BorrowNotFoundException extends NotFoundException {
    public BorrowNotFoundException(int id) {
        super("Borrow with id " + id + " not found");
    }
}
