package com.library.service.impl;


import com.library.dto.request.AuthorFilterRequest;
import com.library.dto.request.AuthorRequest;
import com.library.dto.response.AuthorResponse;
import com.library.dto.response.BookResponse;
import com.library.model.Author;
import com.library.repository.AuthorRepository;
import com.library.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorResponse createAuthor(AuthorRequest request) {
        log.info("Create author");

        Author author = Author.builder()
                .name(request.getName())
                .year(request.getYear())
                .description(request.getDescription())
                .build();

        Author savedAuthor = authorRepository.save(author);

        log.info("Author created successfully with id={}", savedAuthor.getId());

        return AuthorResponse.builder()
                .id(savedAuthor.getId())
                .name(savedAuthor.getName())
                .year(savedAuthor.getYear())
                .description(savedAuthor.getDescription())
                .build();
    }

    public List<AuthorResponse> filter(AuthorFilterRequest filter) {
        log.info("View authors with filter: keyword={}, page={}, size={}",
                filter.getKeyword(),
                filter.getPage(),
                filter.getSize());

        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize());

        return authorRepository.findWithFilter(filter.getKeyword(), pageable);
    }

    public AuthorResponse viewAuthorById(int id) {
        log.info("View author with id={}", id);

        AuthorResponse author = authorRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("Author not found with id={}", id);
                    return new RuntimeException("Author not found");
                });
        log.info("Author found successfully with id = {}", id);
        return author;
    }

    public AuthorResponse updateAuthor(int id, AuthorRequest request) {
        log.info("Update author with id={}", id);

        Author author = authorRepository.findEntityById(id)
                .orElseThrow(() -> {
                    log.warn("Author not found with id={}", id);
                    return new RuntimeException("Author not found");
                });
        author.setName(request.getName());
        author.setYear(request.getYear());
        author.setDescription(request.getDescription());

        Author saved = authorRepository.save(author);

        log.info("Author updated successfully with id = {}", id);

        return AuthorResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .year(saved.getYear())
                .description(saved.getDescription())
                .build();
    }

    public void softDeleteAuthor(int id) {
        log.info("Soft delete author with id={}", id);

        authorRepository.findEntityById(id)
                .orElseThrow(() -> {
                    log.warn("Author not found with id={}", id);
                    return new RuntimeException("Author not found");
                });
        authorRepository.softDelete(id);
        log.info("Author soft deleted successfully with id = {}", id);
    }

    public List<BookResponse> viewAllBooksByAuthor(int authorId) {
        log.info("View all books with authorId = {}", authorId);

        authorRepository.findEntityById(authorId)
                .orElseThrow(() -> {
                    log.warn("Author not found with authorId={}", authorId);
                    return new RuntimeException("Author not found");
                });

        List<BookResponse> books = authorRepository.findBooksByAuthorId(authorId);

        log.info("Books found successfully with author id={}", authorId);

        return books;
    }

    public Author getAvailableAuthorOrThrow(int authorId) {
        return authorRepository.findEntityById(authorId)
                .orElseThrow(() -> {
                    log.warn("Author not found with id={}", authorId);
                    return new RuntimeException("Author not found");
                });
    }
}