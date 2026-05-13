package com.library.dao.impl;

import com.library.dao.BorrowDao;
import com.library.dto.request.BorrowFilterRequest;
import com.library.dto.response.BorrowResponse;
import com.library.model.Borrow;
import com.library.model.BorrowStatus;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class BorrowDaoImpl implements BorrowDao {
    public void insert(Connection conn , Borrow borrow) throws SQLException {
        String sql = "INSERT INTO borrows (book_id, user_id,due_date) VALUES (?, ?, ?)";

        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1,borrow.getBookId());
            ps.setInt(2,borrow.getUserId());
            ps.setTimestamp(3, Timestamp.valueOf(borrow.getDueDate()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                borrow.setId(rs.getInt(1));
            }
        }
    }

    public BorrowResponse getBorrowById(Connection conn , int id) throws SQLException {
        String sql = "SELECT id, book_id, user_id, borrow_date,return_date, due_date, status FROM borrows WHERE id = ? AND is_deleted = FALSE";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                BorrowResponse borrow = new BorrowResponse();
                borrow.setId(rs.getInt("id"));
                borrow.setBookId(rs.getInt("book_id"));
                borrow.setUserId(rs.getInt("user_id"));
                borrow.setBorrowDate(rs.getTimestamp("borrow_date").toLocalDateTime());
                borrow.setReturnDate(rs.getTimestamp("return_date") != null ? rs.getTimestamp("return_date").toLocalDateTime() : null);
                borrow.setDueDate(rs.getTimestamp("due_date").toLocalDateTime());
                borrow.setStatus(BorrowStatus.valueOf(rs.getString("status")));
                return borrow;
            }
            return null;
        }
    }

    public List<BorrowResponse> getBorrowsWithFilter(Connection conn, BorrowFilterRequest filter) throws SQLException {
        String sql = """
                  SELECT id, book_id, user_id, borrow_date,return_date, due_date, status
                  FROM borrows
                  WHERE is_deleted = FALSE
                  AND status = ?::borrow_status
                  LIMIT ?
                  OFFSET ?
                  """;
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, filter.getStatus().toString());
            ps.setInt(2, filter.getSize());
            ps.setInt(3, (filter.getPage() -1) * filter.getSize());
            ResultSet rs = ps.executeQuery();
            List<BorrowResponse> borrowResponse = new java.util.ArrayList<>();
            while(rs.next()) {
                BorrowResponse borrow = new BorrowResponse();
                borrow.setId(rs.getInt("id"));
                borrow.setBookId(rs.getInt("book_id"));
                borrow.setUserId(rs.getInt("user_id"));
                borrow.setBorrowDate(rs.getTimestamp("borrow_date").toLocalDateTime());
                borrow.setReturnDate(rs.getTimestamp("return_date") != null ? rs.getTimestamp("return_date").toLocalDateTime() : null);
                borrow.setDueDate(rs.getTimestamp("due_date").toLocalDateTime());
                borrow.setStatus(BorrowStatus.valueOf(rs.getString("status")));
                borrowResponse.add(borrow);
            }
            return borrowResponse;
        }
    }

    public void delete(Connection conn , int id) throws SQLException {
        String sql = "DELETE FROM borrows WHERE id = ? AND is_deleted = FALSE";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }

    public void softDelete(Connection conn, int id) throws SQLException {
        String sql = "UPDATE borrows SET is_deleted = TRUE WHERE id = ? AND is_deleted = FALSE";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
        }
    }

    public void returnBook(Connection conn , int id) throws SQLException {
        String sql = "UPDATE borrows SET status = 'RETURNED'::borrow_status, return_date = CURRENT_TIMESTAMP WHERE id = ? AND is_deleted = FALSE";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }
}
