package com.library.dao;

import com.library.dto.request.FineFilterRequest;
import com.library.dto.response.FineResponse;
import com.library.model.Fine;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface FineDao {
    void insert(Connection conn, Fine fine) throws SQLException;

    FineResponse getFineById(Connection conn, int id) throws SQLException;

    List<FineResponse> getFinesWithFilter(Connection conn, FineFilterRequest filter) throws SQLException;

    void delete(Connection conn, int id) throws SQLException;

    void softDelete(Connection conn, int id) throws SQLException;

    void payFine(Connection conn, int id) throws SQLException;
}
