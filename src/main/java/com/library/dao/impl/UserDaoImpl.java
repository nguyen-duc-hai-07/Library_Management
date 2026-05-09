package com.library.dao.impl;

import com.library.dao.UserDao;
import com.library.dto.response.BookResponse;
import com.library.dto.response.FineResponse;
import com.library.dto.response.UserResponse;
import com.library.model.FineStatus;
import com.library.model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    public void insert(Connection conn, User user) throws SQLException {
        String sql = "INSERT INTO users (full_name, email, phone_number, password_hash, role, status) VALUES (?, ?, ?, ?, ?::user_role, ?::user_status)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getPasswordHash());
            ps.setString(5, user.getRole().name());
            ps.setString(6, user.getStatus().name());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
        }
    }

    public void update(Connection conn, User user) throws SQLException {
        String sql = "UPDATE users SET" +
                " full_name = ?, email = ?, phone_number = ?, password_hash = ?, role = ?::user_role, status = ?::user_status" +
                " WHERE id = ? AND is_deleted = FALSE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getPasswordHash());
            ps.setString(5, user.getRole().name());
            ps.setString(6, user.getStatus().name());
            ps.setInt(7, user.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ? AND is_deleted = FALSE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public UserResponse getUserById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ? AND is_deleted = FALSE";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UserResponse userResponse = new UserResponse();
                userResponse.setId(rs.getInt("id"));
                userResponse.setFullName(rs.getString("full_name"));
                userResponse.setEmail(rs.getString("email"));
                userResponse.setEmail(rs.getString("email"));
                return userResponse;
            }
            return null;
        }
    }
    public List<BookResponse> getBooksByUserId(Connection conn, int id)
            throws SQLException {

        String sql = """
            SELECT 
                b.id,
                b.title,
                b.isbn,
                b.category_id,
                b.author_id,
                c.name AS category_name,
                a.name AS author_name
            FROM books b
            JOIN categories c ON b.category_id = c.id
            JOIN authors a ON b.author_id = a.id
            JOIN borrows br ON b.id = br.book_id
            WHERE br.user_id = ?
                AND br.is_returned = FALSE
                AND b.is_deleted = FALSE
                AND br.is_deleted = FALSE
                AND a.is_deleted = FALSE
                AND c.is_deleted = FALSE
                AND br.status = 'BORROWING'::borrow_status
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            List<BookResponse> bookResponses = new ArrayList<>();

            while (rs.next()) {

                BookResponse book = new BookResponse();

                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategoryId(rs.getInt("category_id"));
                book.setAuthorId(rs.getInt("author_id"));
                book.setCategoryName(rs.getString("category_name"));
                book.setAuthorName(rs.getString("author_name"));

                bookResponses.add(book);
            }

            return bookResponses;
        }
    }

    public void softDelete(Connection conn, int id)
            throws SQLException {

        String sql = """
            UPDATE users
            SET is_deleted = TRUE
            WHERE id = ?
                AND is_deleted = FALSE
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }

    public List<FineResponse> getFinesByUserId(Connection conn, int id)
            throws SQLException {

        String sql = """
            SELECT
                f.id,
                f.user_id,
                f.borrow_id,
                f.days_late,
                f.status,
                f.paid_at,
                f.fine_amount
            FROM fines f
            WHERE f.user_id = ?
                AND f.is_deleted = FALSE
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            List<FineResponse> fineResponses = new ArrayList<>();

            while (rs.next()) {

                FineResponse fine = new FineResponse();

                fine.setId(rs.getInt("id"));
                fine.setBorrowId(rs.getInt("borrow_id"));
                fine.setUserId(rs.getInt("user_id"));
                fine.setDaysLate(rs.getInt("days_late"));
                fine.setFineAmount(rs.getDouble("fine_amount"));

                fine.setStatus(
                        FineStatus.valueOf(rs.getString("status"))
                );

                fine.setPaidAt(
                        rs.getTimestamp("paid_at") != null
                                ? rs.getTimestamp("paid_at").toLocalDateTime()
                                : null
                );

                fineResponses.add(fine);
            }

            return fineResponses;
        }
    }
}
