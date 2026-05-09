package com.library.dao;

import com.library.dto.response.BookResponse;
import com.library.dto.response.UserResponse;
import com.library.model.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookDao {

    void insert(Connection conn, Book book) throws SQLException;

    BookResponse getBookById(Connection conn, int id) throws SQLException;

    List<BookResponse> getAllBooks(Connection conn) throws SQLException;

    void update(Connection conn, Book book) throws SQLException;

    void delete(Connection conn, int id) throws SQLException;

    void softDelete(Connection conn, int id) throws SQLException;

    void updateQuantity(Connection conn, int id, int quantity) throws SQLException;

    void updateBorrowedQuantity(Connection conn, int id, int quantity) throws SQLException;

    List<UserResponse> getUsersByBookId(Connection conn, int id) throws SQLException;
}
