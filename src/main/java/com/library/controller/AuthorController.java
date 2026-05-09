package com.library.controller;

import com.library.dto.request.AuthorRequest;
import com.library.dto.response.AuthorResponse;
import com.library.dto.response.BookResponse;
import com.library.dto.response.FineResponse;
import com.library.service.AuthorService;
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

    @GetMapping
    public List<AuthorResponse> getAllFines() throws Exception {
        log.info("Get/api/v1/authors");
        return authorService.viewAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorResponse getAuthorById(int id) throws Exception {
        log.info("Get/api/v1/authors/{}",id);
        return authorService.viewAuthorById(id);
    }

    @PostMapping
    public AuthorResponse createAuthor(@RequestBody AuthorRequest author) throws Exception {
        log.info("Post/api/v1/authors");
        return authorService.createAuthor(author);
    }

    @PutMapping("/{id}")
    public AuthorResponse updateAuthor(@PathVariable int id, @RequestBody AuthorRequest author) throws Exception {
        log.info("Put/api/v1/authors/{}",id);
        return authorService.updateAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable int id) throws Exception {
        log.info("Delete/api/v1/authors/{}",id);
        authorService.deleteAuthor(id);
    }

    @PatchMapping("/{id}")
    public void softDeleteAuthor(@PathVariable int id) throws Exception {
        log.info("Patch/api/v1/authors/{}",id);
        authorService.softDeleteAuthor(id);
    }

    @GetMapping("/{id}/books")
    public List<BookResponse> viewAllBooksByAuthor(@PathVariable int id) throws Exception {
        log.info("Get/api/v1/authors/{}/books",id);
        return authorService.viewAllBooksByAuthor(id);
    }
}
