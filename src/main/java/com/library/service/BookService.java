package com.library.service;

import com.library.dto.request.BookRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.UserResponse;

import java.util.List;

public interface BookService {
    BookResponse createBook(BookRequest bookRequest) throws Exception;

    List<BookResponse> viewAllBooks() throws Exception;

    BookResponse viewBookById(int id) throws Exception;

    BookResponse updateBook(int id, BookRequest bookRequest) throws Exception;

    void deleteBook(int id) throws Exception;

    void softDeleteBook(int id) throws Exception;

    List<UserResponse> viewAllUsersByBook(int bookId) throws Exception;

    BookResponse updateQuantity(int id, int quantity) throws Exception;
}
