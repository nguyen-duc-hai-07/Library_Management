package com.library.dao;

import com.library.dto.request.CategoryFilterRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.CategoryResponse;
import com.library.model.Category;

import java.sql.SQLException;
import java.util.*;
import java.sql.Connection;

public interface CategoryDao {
    void insert(Connection conn, Category category) throws SQLException;

    CategoryResponse getCategoryById(Connection conn, int id) throws SQLException;

    List<CategoryResponse> getCategoriesWithFilter(Connection conn, CategoryFilterRequest filter) throws SQLException;

    void update(Connection conn, Category category) throws SQLException;

    void delete(Connection conn, int id) throws SQLException;

    void softDelete(Connection conn, int id) throws SQLException;

    List<BookResponse> getBooksByCategoryId(Connection conn, int id) throws SQLException;
}
