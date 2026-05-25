package com.library.service;

import com.library.dto.request.AuthorFilterRequest;
import com.library.dto.request.AuthorRequest;
import com.library.dto.response.AuthorResponse;
import com.library.dto.response.BookResponse;

import java.util.List;

public interface AuthorService {
    AuthorResponse createAuthor(AuthorRequest request);

    List<AuthorResponse> filter(AuthorFilterRequest filter);

    AuthorResponse viewAuthorById(int id);

    AuthorResponse updateAuthor(int id, AuthorRequest request);

    void softDeleteAuthor(int id);

    List<BookResponse> viewAllBooksByAuthor(int authorId);
}
