package com.library.dao.impl;

import com.library.dao.BookDao;
import com.library.dto.request.BookFilterRequest;
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
    public void insert(Connection conn, Book book) throws SQLException {
        String sql = "INSERT INTO books (title, description, isbn, name, publisher, publish_year, category_id, author_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
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
            if (rs.next()) {
                book.setId(rs.getInt(1));
            }
        }
    }

    public BookResponse getBookById(Connection conn, int id) throws SQLException {
        String sql = "SELECT id, isbn,name, title, author_id, category_id, publisher, publish_year, description, total_quantity, available_quantity FROM books WHERE is_deleted = FALSE AND id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BookResponse book = new BookResponse();
                book.setId(rs.getInt("id"));
                book.setIsbn(rs.getString("isbn"));
                book.setName(rs.getString("name"));
                book.setTitle(rs.getString("title"));
                book.setAuthorId(rs.getInt("author_id"));
                book.setCategoryId(rs.getInt("category_id"));
                book.setPublisher(rs.getString("publisher"));
                book.setPublishYear(rs.getString("publish_year"));
                book.setDescription(rs.getString("description"));
                book.setTotalQuantity(rs.getInt("total_quantity"));
                book.setAvailableQuantity(rs.getInt("available_quantity"));
                return book;
            }
            return null;
        }
    }

    public List<BookResponse> getBooksWithFilter(Connection conn, BookFilterRequest filter) throws SQLException {
        String sql = """
                SELECT b.id,b.name,b.isbn,  b.title,  b.author_id, a.name AS author_name, b.category_id,c.name AS category_name,  b.publisher,  b.publish_year, b.description, b.total_quantity, b.available_quantity
                FROM books b
                JOIN authors a ON b.author_id = a.id
                JOIN categories c ON b.category_id = c.id
                WHERE b.is_deleted = FALSE
                AND a.is_deleted = FALSE
                AND c.is_deleted = FALSE
                AND b.title ILIKE ?
                LIMIT ?
                OFFSET ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + filter.getKeyword() + "%");
            ps.setInt(2, filter.getSize());
            ps.setInt(3, (filter.getPage() - 1) * filter.getSize());
            ResultSet rs = ps.executeQuery();
            List<BookResponse> bookResponse = new java.util.ArrayList<>();
            while (rs.next()) {
                BookResponse books = new BookResponse();
                books.setId(rs.getInt("id"));
                books.setName(rs.getString("name"));
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
            }
            return bookResponse;
        }
    }

    public void update(Connection conn, Book book) throws SQLException {
        String sql = "UPDATE books SET title = ?, description = ?, isbn = ?, name = ?, publisher = ?, publish_year = ?, category_id = ?, author_id = ? WHERE id = ? AND is_deleted = FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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

    public void delete(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ? AND is_deleted = FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void softDelete(Connection conn, int id) throws SQLException {
        String sql = "UPDATE books SET is_deleted = TRUE WHERE id = ? AND is_deleted = FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
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

    public List<UserResponse> getUsersByBookId(Connection conn, int id) throws SQLException {
        String sql = """
                SELECT u.id,u.full_name,u.email,u.phone_number
                FROM users u
                JOIN borrows b ON u.id = b.user_id
                WHERE b.book_id = ? AND b.is_deleted = FALSE
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            List<UserResponse> userResponse = new java.util.ArrayList<>();
            while (rs.next()) {
                UserResponse user = new UserResponse();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                userResponse.add(user);
            }
            return userResponse;
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
