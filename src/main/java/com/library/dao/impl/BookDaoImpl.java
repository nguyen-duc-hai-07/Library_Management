package com.library.dao.impl;

import com.library.dao.BookDao;
import com.library.dto.response.BookResponse;
import com.library.dto.response.UserResponse;
import com.library.model.Book;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    public void insert(Connection conn , Book book) throws SQLException {
        String sql = "INSERT INTO books (title, description, isbn, name, publisher, publish_year, category_id, author_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getDescription());
            ps.setString(3, book.getIsbn());
            ps.setString(4, book.getName());
            ps.setString(5, book.getPublisher());
            ps.setString(6, book.getPublishYear());
            ps.setInt(7, book.getCategoryId());
            ps.setInt(8, book.getAuthorId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                book.setId(rs.getInt(1));
            }
        }
    }

    public BookResponse getBookById(Connection conn , int id) throws SQLException {
        String sql = "SELECT id, isbn, title, author_id, category_id, publisher, publish_year, description, total_quantity, available_quantity FROM books WHERE is_deleted = FALSE";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                BookResponse book = new BookResponse();
                book.setId(rs.getInt(1));
                book.setIsbn(rs.getString(2));
                book.setTitle(rs.getString(3));
                book.setAuthorId(rs.getInt(4));
                book.setCategoryId(rs.getInt(5));
                book.setPublisher(rs.getString(6));
                book.setPublishYear(rs.getString(7));
                book.setDescription(rs.getString(8));
                book.setTotalQuantity(rs.getInt(9));
                book.setAvailableQuantity(rs.getInt(10));
                return book;
            }
            return null;
        }
    }

    public List<BookResponse> getAllBooks(Connection conn) throws SQLException {
        String sql = """
                SELECT b.id,b.isbn,  b.title,  b.author_id, a.name AS author_name, b.category_id,c.name AS category_name,  b.publisher,  b.publish_year, b.description, b.total_quantity, b.available_quantity
                FROM books b
                JOIN authors a ON b.author_id = a.id
                JOIN categories c ON b.category_id = c.id
                WHERE b.is_deleted = FALSE
                AND a.is_deleted = FALSE
                AND c.is_deleted = FALSE
                """;

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<BookResponse> bookResponse = new java.util.ArrayList<>();
            while(rs.next()) {
                BookResponse books = new BookResponse();
                books.setId(rs.getInt("id"));
                books.setIsbn(rs.getString("isbn"));
                books.setTitle(rs.getString("title"));
                books.setAuthorId(rs.getInt("author_id"));
                books.setAuthorName(rs.getString("author_name"));
                books.setCategoryId(rs.getInt("category_id"));
                books.setCategoryName(rs.getString("category_name"));
                books.setPublisher(rs.getString("publisher"));
                books.setPublishYear(rs.getString("publish_year"));
                books.setDescription(rs.getString("description"));
                books.setTotalQuantity(rs.getInt("total_quantity"));
                books.setAvailableQuantity(rs.getInt("available_quantity"));
                bookResponse.add(books);
                return bookResponse;
            }
            return null;
        }
    }

    public void update(Connection conn , Book book) throws SQLException {
        String sql = "UPDATE books SET title = ?, description = ?, isbn = ?, name = ?, publisher = ?, publish_year = ?, category_id = ?, author_id = ? WHERE id = ? AND is_deleted = FALSE";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getDescription());
            ps.setString(3, book.getIsbn());
            ps.setString(4, book.getName());
            ps.setString(5, book.getPublisher());
            ps.setString(6, book.getPublishYear());
            ps.setInt(7, book.getCategoryId());
            ps.setInt(8, book.getAuthorId());
            ps.setInt(9, book.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Connection conn , int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ? AND is_deleted = FALSE";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }

    public void softDelete(Connection conn, int id) throws SQLException {
        String sql = "UPDATE books SET is_deleted = TRUE WHERE id = ? AND is_deleted = FALSE";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }

    public void updateQuantity(Connection conn, int id, int quantity) throws SQLException {
        String sql = "UPDATE books SET " +
                "total_quantity = total_quantity + ?, " +
                "available_quantity = available_quantity + ? " +
                "WHERE id = ? AND is_deleted = FALSE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, quantity);
            ps.setInt(3, id);
            ps.executeUpdate();
        }
    }

    public List<UserResponse> getUsersByBookId(Connection conn , int id) throws SQLException {
        String sql = """
                SELECT u.id,u.full_name,u.email,u.phone_number
                FROM users u
                JOIN borrows b ON u.id = b.user_id
                WHERE b.book_id = ? AND b.is_deleted = FALSE
                """;
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            List<UserResponse> userResponse = new java.util.ArrayList<>();
            while(rs.next()) {
                UserResponse user = new UserResponse();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                userResponse.add(user);
                return userResponse;
            }
            return null;
        }
    }
    public void updateBorrowedQuantity(Connection conn, int id, int quantity) throws SQLException {
        String sql = "UPDATE books SET " +
                "available_quantity = available_quantity + ? " +
                "WHERE id = ? AND is_deleted = FALSE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
}
