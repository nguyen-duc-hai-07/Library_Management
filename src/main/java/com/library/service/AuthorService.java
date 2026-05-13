package com.library.service;

import com.library.dto.request.AuthorFilterRequest;
import com.library.dto.request.AuthorRequest;
import com.library.dto.response.AuthorResponse;
import com.library.dto.response.BookResponse;

import java.util.List;

public interface AuthorService {
    AuthorResponse createAuthor(AuthorRequest request) throws Exception;

    List<AuthorResponse> viewAuthorsWithFilter(AuthorFilterRequest filter) throws Exception;

    AuthorResponse viewAuthorById(int id) throws Exception;

    AuthorResponse updateAuthor(int id, AuthorRequest request) throws Exception;

    void deleteAuthor(int id) throws Exception;

    void softDeleteAuthor(int id) throws Exception;

    List<BookResponse> viewAllBooksByAuthor(int authorId) throws Exception;
}
