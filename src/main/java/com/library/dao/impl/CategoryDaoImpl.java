package com.library.dao.impl;

import com.library.dao.CategoryDao;
import com.library.dto.request.CategoryFilterRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.CategoryResponse;
import com.library.model.Category;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {
    public void insert(Connection conn, Category category) throws SQLException {
        String sql = "INSERT INTO categories (name) VALUES (?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, category.getName());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                category.setId(rs.getInt(1));
            }
        }
    }

    public CategoryResponse getCategoryById(Connection conn, int id) throws SQLException {
        String sql = "SELECT id,name,is_deleted FROM categories WHERE id = ? AND is_deleted = FALSE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CategoryResponse category = new CategoryResponse();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setIsDeleted(rs.getBoolean("is_deleted"));
                return category;
            }
            return null;
        }
    }

    public List<CategoryResponse> getCategoriesWithFilter(Connection conn, CategoryFilterRequest filter) throws SQLException {
        String sql = """
                 SELECT id,name
                 FROM categories
                 WHERE is_deleted = FALSE
                 AND name ILIKE ?
                 LIMIT ?
                 OFFSET ?
                 """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + filter.getKeyword() + "%");
            ps.setInt(2, filter.getSize());
            ps.setInt(3, (filter.getPage() - 1) * filter.getSize());
            ResultSet rs = ps.executeQuery();
            List<CategoryResponse> categoryResponse = new ArrayList<>();
            while (rs.next()) {
                CategoryResponse categories = new CategoryResponse();
                categories.setId(rs.getInt("id"));
                categories.setName(rs.getString("name"));
                categoryResponse.add(categories);
            }
            return categoryResponse;
        }
    }

    public void update(Connection conn, Category category) throws SQLException {
        String sql = "UPDATE categories SET name = ? WHERE id = ? AND is_deleted = FALSE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getId());
            ps.executeUpdate();
        }
    }

    public void softDelete(Connection conn, int id) throws SQLException {
        String sql = "UPDATE categories SET is_deleted = TRUE WHERE id = ? AND is_deleted = FALSE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
        }
    }

    public void delete(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM categories WHERE id = ? AND is_deleted = FALSE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<BookResponse> getBooksByCategoryId(Connection conn, int id) throws SQLException {
        String sql = """
                SELECT b.id,b.title,b.isbn,b.category_id,b.author_id,c.name AS category_name,a.name AS author_name
                FROM books b
                JOIN categories c ON b.category_id = c.id
                JOIN authors a ON b.author_id = a.id
                WHERE b.category_id = ?
                AND b.is_deleted = FALSE
                AND c.is_deleted = FALSE
                AND a.is_deleted = FALSE
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            List<BookResponse> bookResponse = new ArrayList<>();
            while (rs.next()) {
                BookResponse books = new BookResponse();
                books.setId(rs.getInt("id"));
                books.setTitle(rs.getString("title"));
                books.setIsbn(rs.getString("isbn"));
                books.setCategoryId(rs.getInt("category_id"));
                books.setAuthorId(rs.getInt("author_id"));
                books.setAuthorName(rs.getString("author_name"));
                books.setCategoryName(rs.getString("category_name"));
                bookResponse.add(books);
            }
            return bookResponse;
        }
    }
}

