package com.library.controller;

import com.library.dto.request.BookRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.UserResponse;
import com.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookResponse> getAllBooks() throws Exception {
        log.info("Get/api/v1/books");
        return bookService.viewAllBooks();
    }

    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable int id) throws Exception {
        log.info("Get/api/v1/books/{}",id);
        return bookService.viewBookById(id);
    }

    @PostMapping
    public BookResponse createBook(@RequestBody BookRequest book) throws Exception {
        log.info("Post/api/v1/books");
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    public BookResponse updateBook(@PathVariable int id, @RequestBody BookRequest book) throws Exception {
        log.info("Put/api/v1/books/{}",id);
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable int id) throws Exception {
        log.info("Delete/api/v1/books/{}",id);
        bookService.deleteBook(id);
    }

    @PatchMapping("/{id}")
    public void softDeleteBook(@PathVariable int id) throws Exception {
        log.info("Patch/api/v1/books/{}",id);
        bookService.softDeleteBook(id);
    }

    @GetMapping("/{id}/users")
    public List<UserResponse> viewAllUsersByBook(@PathVariable int id) throws Exception {
        log.info("Get/api/v1/books/{}/users",id);
        return bookService.viewAllUsersByBook(id);
    }

    @PatchMapping("/{id}/quantity")
    public BookResponse updateBookQuantity(@PathVariable int id, @RequestParam int quantity) throws Exception {
        log.info("Get/api/v1/books/{}/quantity",id);
        return bookService.updateQuantity(id, quantity);
    }
}
