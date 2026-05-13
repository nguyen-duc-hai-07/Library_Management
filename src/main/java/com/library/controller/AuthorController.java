package com.library.controller;

import com.library.dto.request.AuthorFilterRequest;
import com.library.dto.request.AuthorRequest;
import com.library.dto.response.AuthorResponse;
import com.library.dto.response.BookResponse;
import com.library.dto.response.FineResponse;
import com.library.service.AuthorService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {
    private final AuthorService authorService;
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/filter")
    public List<AuthorResponse> getAll(@RequestBody AuthorFilterRequest filter) throws Exception {
        log.info("View authors");

        log.debug(
                "Author filter request: keyword={}, page={}, size={}",
                filter.getKeyword(),
                filter.getPage(),
                filter.getSize()
        );

        return authorService.viewAuthorsWithFilter(filter);
    }

    @GetMapping("/{id}")
    public AuthorResponse getById(@PathVariable int id) throws Exception {
        log.info("View author");

        log.debug("Author id={}", id);

        return authorService.viewAuthorById(id);
    }

    @PostMapping
    public AuthorResponse create(@RequestBody AuthorRequest author) throws Exception {
        log.info("Create author");

        return authorService.createAuthor(author);
    }

    @PutMapping("/{id}")
    public AuthorResponse update(@PathVariable int id, @RequestBody AuthorRequest author) throws Exception {
        log.info("Update author");

        log.debug("Update request: id={} ", id);

        return authorService.updateAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) throws Exception {
        log.info("Delete author");

        log.debug("Delete id={}", id);

        authorService.deleteAuthor(id);
    }

    @PatchMapping("/{id}")
    public void softDelete(@PathVariable int id) throws Exception {
        log.info("Soft delete author");

        log.debug("Soft delete id={}", id);

        authorService.softDeleteAuthor(id);
    }

    @GetMapping("/{id}/books")
    public List<BookResponse> getBooks(@PathVariable int id) throws Exception {
        log.info("View books by author");

        log.debug("Author id={}", id);

        return authorService.viewAllBooksByAuthor(id);
    }
}
