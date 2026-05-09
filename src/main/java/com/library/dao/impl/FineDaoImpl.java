package com.library.dao.impl;

import com.library.dao.FineDao;
import com.library.dto.response.FineResponse;
import com.library.model.Fine;
import com.library.model.FineStatus;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FineDaoImpl implements FineDao {
    public void insert(Connection conn , Fine fine) throws SQLException {
        String sql = "INSERT INTO fines (borrow_id,user_id,days_late,fine_amount) VALUES (?,?,?,?)";

        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1,fine.getBorrowId());
            ps.setInt(2,fine.getUserId());
            ps.setInt(3,fine.getDaysLate());
            ps.setDouble(4,fine.getFineAmount());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                fine.setId(rs.getInt(1));
            }
        }
    }

    public FineResponse getFineById(Connection conn , int id) throws SQLException {
        String sql = "SELECT id, borrow_id, user_id, days_late, fine_amount, status, paid_at FROM fines WHERE id = ? AND is_deleted = FALSE";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                FineResponse fine = new FineResponse();

                fine.setId(rs.getInt("id"));
                fine.setBorrowId(rs.getInt("borrow_id"));
                fine.setUserId(rs.getInt("user_id"));
                fine.setDaysLate(rs.getInt("days_late"));
                fine.setFineAmount(rs.getDouble("fine_amount"));
                fine.setStatus(FineStatus.valueOf(rs.getString("status")));
                fine.setPaidAt(rs.getTimestamp("paid_at") != null ? rs.getTimestamp("paid_at").toLocalDateTime() : null);

                return fine;
            }
            return null;
        }
    }

    public List<FineResponse> getAllFines(Connection conn) throws SQLException {
        String sql = "SELECT id, borrow_id, user_id, days_late, fine_amount, status, paid_at FROM fines WHERE is_deleted = FALSE";
        try(PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()
        ) {
            List<FineResponse> fineResponse = new ArrayList<>();
            while(rs.next()) {
                FineResponse fine = new FineResponse();
                fine.setId(rs.getInt("id"));
                fine.setBorrowId(rs.getInt("borrow_id"));
                fine.setUserId(rs.getInt("user_id"));
                fine.setDaysLate(rs.getInt("days_late"));
                fine.setFineAmount(rs.getDouble("fine_amount"));
                fine.setStatus(FineStatus.valueOf(rs.getString("status")));
                fine.setPaidAt(rs.getTimestamp("paid_at") != null ? rs.getTimestamp("paid_at").toLocalDateTime() : null);
                fineResponse.add(fine);
                return fineResponse;
            }
            return null;
        }
    }

    public void delete(Connection conn , int id) throws SQLException {
        String sql = "DELETE FROM fines WHERE id = ? AND is_deleted = FALSE";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }

    public void softDelete(Connection conn, int id) throws SQLException {
        String sql = "UPDATE fines SET is_deleted = TRUE WHERE id = ? AND is_deleted = FALSE";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
        }
    }

    public void payFine(Connection conn , int id) throws SQLException {
        String sql = "UPDATE fines SET status = 'PAID'::fine_status, paid_at = CURRENT_TIMESTAMP WHERE id = ? AND is_deleted = FALSE";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,id);
            ps.executeUpdate();
        }
    }
}
