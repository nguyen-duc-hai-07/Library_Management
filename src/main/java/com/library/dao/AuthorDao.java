package com.library.dao;

import com.library.dto.response.BookResponse;
import com.library.dto.response.AuthorResponse;
import com.library.model.Author;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AuthorDao {
    void insert(Connection conn , Author author) throws SQLException;

    AuthorResponse getAuthorById(Connection conn , int id) throws SQLException;

    List<AuthorResponse> getAllAuthors(Connection conn) throws SQLException;

    void update(Connection conn , Author author) throws SQLException;

    void delete(Connection conn , int id) throws SQLException;

    void softDelete(Connection conn, int id) throws SQLException;

    List<BookResponse> getBooksByAuthorId(Connection conn , int id) throws SQLException;
}
