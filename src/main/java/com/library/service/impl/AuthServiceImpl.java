package com.library.service.impl;

import com.library.config.DBConnectionPool;
import com.library.dao.UserDao;
import com.library.dto.request.LoginRequest;
import com.library.dto.request.RegisterRequest;
import com.library.model.User;
import com.library.model.UserRole;
import com.library.model.UserStatus;
import com.library.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.UUID;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserDao userDao;
    private final DBConnectionPool pool = DBConnectionPool.getInstance();
    public AuthServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    public void register(RegisterRequest request) throws Exception {
        log.info("Register user");
        Connection conn = null;

        try {
            conn = pool.getConnection();
            User user = new User();
            user.setFullName(request.getFullName());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setEmail(request.getEmail());
            user.setPasswordHash(request.getPassword());
            user.setRole(UserRole.READER);
            user.setStatus(UserStatus.ACTIVE);
            userDao.insert(conn, user);
            conn.commit();
            log.info("Register successfully: {}", request.getEmail());
        } catch (Exception e) {
            log.error("Register failed: {}", e.getMessage());
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public String login(LoginRequest request) throws Exception{
        log.info("Login user");
        Connection conn = null;

        try {
            conn = pool.getConnection();
            User user = userDao.getUserByEmail(conn, request.getEmail());
            if(user == null || !user.getPasswordHash().equals(request.getPassword())) {
                throw new Exception("Incorrect email or password");
            }
            conn.commit();

            log.info("Login successfully: {}", request.getEmail());

            return UUID.randomUUID().toString();
        } catch (Exception e) {
            log.error("login failed: {}", e.getMessage());
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
