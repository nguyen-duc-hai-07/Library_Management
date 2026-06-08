package com.library.exception.category;

import com.library.exception.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(int id) {
        super("Category with id " + id + " not found");
    }
}
