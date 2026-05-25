package com.library.service.impl;

import com.library.dto.request.BookFilterRequest;
import com.library.dto.request.BookRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.UserResponse;
import com.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    @Override
    public BookResponse createBook(BookRequest bookRequest) throws Exception {
        return null;
    }

    @Override
    public List<BookResponse> filter(BookFilterRequest filter) throws Exception {
        return List.of();
    }

    @Override
    public BookResponse viewBookById(int id) throws Exception {
        return null;
    }

    @Override
    public BookResponse updateBook(int id, BookRequest bookRequest) throws Exception {
        return null;
    }

    @Override
    public void deleteBook(int id) throws Exception {

    }

    @Override
    public void softDeleteBook(int id) throws Exception {

    }

    @Override
    public List<UserResponse> viewAllUsersByBook(int bookId) throws Exception {
        return List.of();
    }

    @Override
    public BookResponse updateQuantity(int id, int quantity) throws Exception {
        return null;
    }
}
