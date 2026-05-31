package com.library.service;

import com.library.dto.request.BookFilterRequest;
import com.library.dto.request.BookRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.UserResponse;
import com.library.model.Book;

import java.util.List;

public interface BookService {
    Book createBook(Book book) ;

    List<BookResponse> filter(BookFilterRequest filter) ;

    BookResponse viewBookById(int id) ;

    Book updateBook(int id, Book book) ;

    void softDeleteBook(int id) ;

    List<UserResponse> viewAllUsersByBook(int bookId) ;

    BookResponse updateQuantity(int id, int quantity) ;

    Book getAvailableBookOrThrow(int bookId);

    void updateStock(int bookId, int quantity);
}
