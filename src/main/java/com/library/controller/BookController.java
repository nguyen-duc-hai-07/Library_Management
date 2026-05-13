package com.library.controller;

import com.library.dto.request.BookFilterRequest;
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

    @PostMapping("/filter")
    public List<BookResponse> getAll(@RequestBody BookFilterRequest filter) throws Exception {

        log.info("View books");

        log.debug(
                "Book filter request: keyword={}, page={}, size={}",
                filter.getKeyword(),
                filter.getPage(),
                filter.getSize()
        );

        return bookService.viewBooksWithFilter(filter);
    }

    @GetMapping("/{id}")
    public BookResponse getById(@PathVariable int id) throws Exception {

        log.info("View book");

        log.debug("Book id={}", id);

        return bookService.viewBookById(id);
    }

    @PostMapping
    public BookResponse create(@RequestBody BookRequest book) throws Exception {

        log.info("Create book");

        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    public BookResponse update(@PathVariable int id, @RequestBody BookRequest book) throws Exception {

        log.info("Update book");

        log.debug("Update request: id={}", id);

        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) throws Exception {

        log.info("Delete book");

        log.debug("Delete id={}", id);

        bookService.deleteBook(id);
    }

    @PatchMapping("/{id}")
    public void softDelete(@PathVariable int id) throws Exception {

        log.info("Soft delete book");

        log.debug("Soft delete id={}", id);

        bookService.softDeleteBook(id);
    }

    @GetMapping("/{id}/users")
    public List<UserResponse> getUsers(@PathVariable int id) throws Exception {

        log.info("View users by book");

        log.debug("Book id={}", id);

        return bookService.viewAllUsersByBook(id);
    }

    @PatchMapping("/{id}/quantity")
    public BookResponse updateQuantity(@PathVariable int id, @RequestParam int quantity) throws Exception {

        log.info("Update book quantity");

        log.debug("Update quantity request: id={}, quantity={}", id, quantity);

        return bookService.updateQuantity(id, quantity);
    }
}