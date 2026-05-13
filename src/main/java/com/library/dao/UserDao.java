package com.library.dao;

import com.library.dto.request.UserFilterRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.FineResponse;
import com.library.dto.response.UserResponse;
import com.library.model.User;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;

public interface UserDao {
    List<UserResponse> getUsersWihFilter(Connection conn , UserFilterRequest filter) throws Exception;

    void insert(Connection conn, User user) throws SQLException;

    void update(Connection conn, User user) throws SQLException;

    UserResponse getUserById(Connection conn, int id) throws SQLException;

    List<BookResponse> getBooksByUserId(Connection conn, int id) throws SQLException;

    void delete(Connection conn, int id) throws SQLException;

    void softDelete(Connection conn, int id) throws SQLException;

    List<FineResponse> getFinesByUserId(Connection conn, int id) throws SQLException;
}
