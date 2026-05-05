package com.library.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

public class DBConnectionPool {

    private static DBConnectionPool instance;
    private HikariDataSource dataSource;

    private final String URL = "jdbc:postgresql://localhost:2310/library";
    private final String USER = "postgres";
    private final String PASSWORD = "123456";


    private DBConnectionPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setAutoCommit(false);

        dataSource = new HikariDataSource(config);
    }

    // Lấy instance duy nhất
    public static DBConnectionPool getInstance() {
        if (instance == null) {
            instance = new DBConnectionPool();
        }
        return instance;
    }

    // Lấy connection
    public Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
}