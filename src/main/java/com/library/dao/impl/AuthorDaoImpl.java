package com.library.dao.impl;

import com.library.dao.AuthorDao;
import com.library.dto.response.AuthorResponse;
import com.library.dto.response.BookResponse;
import com.library.model.Author;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class AuthorDaoImpl implements AuthorDao {
    public void insert(Connection conn , Author author) throws SQLException {
        String sql = "INSERT INTO authors (name, birth_year, description) VALUES (?, ?, ?)";

        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, author.getName());
            ps.setInt(2, author.getYear());
            ps.setString(3,author.getDescription());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                author.setId(rs.getInt(1));
            }
        }
    }

    public AuthorResponse getAuthorById(Connection conn , int id) throws SQLException {
        String sql = "SELECT id,name,birth_year,description FROM authors WHERE id = ? AND is_deleted = FALSE";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                AuthorResponse author = new AuthorResponse();
                author.setId(rs.getInt("id"));
                author.setName(rs.getString("name"));
                author.setYear(rs.getInt("birth_year"));
                author.setDescription(rs.getString("description"));
                return author;
            }
            return null;
        }
    }

    public List<AuthorResponse> getAllAuthors(Connection conn) throws SQLException {
        String sql = "SELECT id,name,birth_year,description FROM authors AND is_deleted = FALSE";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<AuthorResponse> authorResponse = new java.util.ArrayList<>();
            while(rs.next()) {
                AuthorResponse author = new AuthorResponse();
                author.setId(rs.getInt("id"));
                author.setName(rs.getString("name"));
                author.setYear(rs.getInt("birth_year"));
                author.setDescription(rs.getString("description"));
                authorResponse.add(author);
            }
            return authorResponse;
        }
    }

    public void update(Connection conn , Author author) throws SQLException {
        String sql = "UPDATE authors SET name = ?, birth_year = ?, description = ? WHERE id = ? AND is_deleted = FALSE";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,author.getName());
            ps.setInt(2,author.getYear());
            ps.setString(3,author.getDescription());
            ps.setInt(4,author.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Connection conn , int id) throws SQLException {
        String sql = "DELETE FROM authors WHERE id = ? AND is_deleted = FALSE";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }

    public void softDelete(Connection conn, int id) throws SQLException {
        String sql = "UPDATE authors SET is_deleted = TRUE WHERE id = ? AND is_deleted = FALSE";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }

    public List<BookResponse> getBooksByAuthorId(Connection conn , int id) throws SQLException {
        String sql = """
                SELECT b.id,b.title,b.isbn,b.category_id,b.author_id,c.name AS category_name,a.name AS author_name
                FROM books b
                JOIN categories c ON b.category_id = c.id
                JOIN authors a ON b.author_id = a.id
                WHERE b.author_id = ?
                AND b.is_deleted = FALSE
                AND c.is_deleted = FALSE
                AND a.is_deleted = FALSE
                """;

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            List<BookResponse> bookResponse = new java.util.ArrayList<>();
            while(rs.next()) {
                BookResponse books = new BookResponse();
                books.setId(rs.getInt("id"));
                books.setTitle(rs.getString("title"));
                books.setIsbn(rs.getString("isbn"));
                books.setAuthorName(rs.getString("author_name"));
                books.setCategoryName(rs.getString("category_name"));
                books.setCategoryId(rs.getInt("category_id"));
                books.setAuthorId(rs.getInt("author_id"));
                bookResponse.add(books);
                return bookResponse;
            }
            return null;
        }
    }
}
