package com.library.dao;

import com.library.dto.request.BorrowFilterRequest;
import com.library.dto.response.BorrowResponse;
import com.library.model.Borrow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BorrowDao {
    void insert(Connection conn, Borrow borrow) throws SQLException;

    BorrowResponse getBorrowById(Connection conn, int id) throws SQLException;

    List<BorrowResponse> getBorrowsWithFilter(Connection conn, BorrowFilterRequest filter) throws SQLException;

    void delete(Connection conn, int id) throws SQLException;

    void softDelete(Connection conn, int id) throws SQLException;

    void returnBook(Connection conn, int id) throws SQLException;
}
