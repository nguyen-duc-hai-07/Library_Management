package com.library.exception.book;

import com.library.exception.NotFoundException;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(int id) {
        super("Book with id " + id + " not found");
    }
}
