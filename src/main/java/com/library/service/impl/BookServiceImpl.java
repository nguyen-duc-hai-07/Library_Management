package com.library.service.impl;

import com.library.dto.request.BookFilterRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.UserResponse;
import com.library.exception.book.BookBadRequestException;
import com.library.exception.book.BookNotFoundException;
import com.library.model.Book;
import com.library.repository.BookRepository;
import com.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(Book book) {
        log.info("Create book");

        return bookRepository.save(book);
    }

    @Override
    public List<BookResponse> filter(BookFilterRequest filter) {
        log.info("View books with filter: keyword={}, page={}, size={}",
                filter.getKeyword(),
                filter.getPage(),
                filter.getSize());

        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize());

        return bookRepository.findWithFilter(filter.getKeyword(), pageable);
    }

    @Override
    public BookResponse viewBookById(int id) {
        log.info("View book with id={}", id);

        BookResponse books = getBookResponseOrThrow(id); //hàm check id tồn tại

        log.info("Book found successfully with id={}", id);

        return books;
    }

    @Override
    public Book updateBook(int id, Book book) {
        log.info("Update book with id={}", id);

        getBookOrThrow(id);

        return bookRepository.save(book);
    }

    @Override
    public void softDeleteBook(int id) {
        log.info("Soft delete book with id={}", id);

        getBookOrThrow(id);

        bookRepository.softDelete(id);

        log.info("Book soft deleted successfully with id = {}", id);
    }

    @Override
    public List<UserResponse> viewAllUsersByBook(int bookId) {
        log.info("View all users with bookId = {}", bookId);

        getBookResponseOrThrow(bookId);

        List<UserResponse> users = bookRepository.findUsersByBookId(bookId);

        log.info("Users found successfully with book id={}", bookId);

        return users;
    }

    @Override
    public BookResponse updateQuantity(int id, int quantity) {
        log.info("Update quantity of book with id={}", id);

        getBookOrThrow(id);

        bookRepository.updateAvailableQuantity(id, quantity);

        log.info("Quantity updated successfully with id={}", id);

        return getBookResponseOrThrow(id);
    }

    private Book getBookOrThrow(int id) {
        return bookRepository.findEntityById(id)
                .orElseThrow(() -> {
                    log.warn("Book not found with id={}", id);
                    return new BookNotFoundException(id);
                });
    }

    private BookResponse getBookResponseOrThrow(int id) {
        return bookRepository.findActiveBookById(id)
                .orElseThrow(() -> {
                    log.warn("Book not found with id={}", id);
                    return new BookNotFoundException(id);
                });
    }

    public Book getAvailableBookOrThrow(int bookId) {
        Book book = bookRepository.findEntityById(bookId)
                .orElseThrow(() -> {
                    log.warn("Book not found with id={}", bookId);
                    return new BookNotFoundException(bookId);
                });

        if (book.getAvailableQuantity() <= 0) {
            throw new BookBadRequestException("Book is out of stock");
        }

        return book;
    }

    public void updateStock(int bookId, int quantity) {
        log.info("Update stock of book with id={}", bookId);

        bookRepository.updateQuantity(bookId, quantity);
    }
}
