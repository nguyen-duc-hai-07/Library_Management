package com.library.service.impl;

import com.library.config.DBConnectionPool;
import com.library.dao.UserDao;
import com.library.dto.request.UserRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.FineResponse;
import com.library.dto.response.UserResponse;
import com.library.model.User;
import com.library.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;


@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final DBConnectionPool pool = DBConnectionPool.getInstance();
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserResponse createUser(UserRequest request) throws Exception {
        Connection conn = null;
        User user = new User(
                request.getFullName(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getPasswordHash(),
                request.getRole(),
                request.getStatus()
        );
        log.info("Create user");

        try {
            conn = pool.getConnection();

            userDao.insert(conn, user);

            conn.commit();

            log.info("User created successfully with id = {}", user.getId());

            return new UserResponse(
                    user.getId(),
                    user.getEmail(),
                    user.getFullName(),
                    user.getPhoneNumber(),
                    user.getRole(),
                    user.getStatus(),
                    user.getCreatedAt()
            );
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage(), e);
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

    public UserResponse viewUserById(int id) throws Exception {
        Connection conn = null;
        log.info("View user by id = {}", id);
        try {
            conn = pool.getConnection();

            UserResponse user = userDao.getUserById(conn , id);

            conn.commit();

            log.info("User found successfully with id = {}", id);

           return user;
        } catch (Exception e) {
            log.error("View user by id = {} failed: {}", id, e.getMessage(), e);
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

    public UserResponse updateUser(int id, UserRequest request) throws Exception {
        Connection conn = null;
        User user = new User(
              request.getFullName(),
              request.getEmail(),
              request.getPhoneNumber(),
              request.getPasswordHash(),
              request.getRole(),
              request.getStatus()
        );
        user.setId(id);
        log.info("Update user by id = {}", id);
        try {
            conn = pool.getConnection();

            UserResponse existingUser = userDao.getUserById(conn, id);
            if (existingUser == null) {
                log.warn("User not found with id={}", id);
                throw new Exception("User not found");
            }

            userDao.update(conn, user);

            conn.commit();

            log.info("User updated successfully with id = {}", id);

            return new UserResponse(
                    user.getId(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getRole(),
                    user.getStatus(),
                    user.getCreatedAt()
            );
        } catch (Exception e) {
            log.error("Update user by id = {} failed: {}", id, e.getMessage(), e);
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

    public void deleteUser(int id) throws Exception {
        Connection conn = null;
        log.info("Delete user by id = {}", id);
        try {
            conn = pool.getConnection();

            UserResponse existingUser = userDao.getUserById(conn, id);
            if (existingUser == null) {
                log.warn("User not found with id={}", id);
                throw new Exception("User not found");
            }

            userDao.delete(conn, id);

            conn.commit();

            log.info("User deleted successfully with id = {}", id);
        } catch (Exception e) {
            log.error("Delete user by id = {} failed: {}", id, e.getMessage(), e);
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

    public void softDeleteUser(int id) throws Exception {
        Connection conn = null;
        log.info("Soft delete user by id = {}", id);
        try {
            conn = pool.getConnection();

            UserResponse existingUser = userDao.getUserById(conn, id);
            if (existingUser == null) {
                log.warn("User not found with id={}", id);
                throw new Exception("User not found");
            }

            userDao.softDelete(conn, id);

            conn.commit();

            log.info("User soft deleted successfully with id = {}", id);
        } catch (Exception e) {
            log.error("Soft delete user by id = {} failed: {}", id, e.getMessage(), e);
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

    public List<BookResponse> viewAllBooksByUser(int userId) throws Exception {
        Connection conn = null;
        log.info("View all books by user id = {}", userId);
        try {
            conn = pool.getConnection();

            UserResponse existingUser = userDao.getUserById(conn, userId);
            if (existingUser == null) {
                log.warn("User not found with id={}", userId);
                throw new Exception("User not found");
            }

            List<BookResponse> books = userDao.getBooksByUserId(conn, userId);

            conn.commit();

            log.info("Books found successfully with user id = {}", userId);

            return books;
        } catch (Exception e) {
            log.error("View all books by user id = {} failed: {}", userId, e.getMessage(), e);
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

    public List<FineResponse> viewAllFinesByUser(int userId) throws Exception {
        Connection conn = null;
        log.info("View all fines by user id = {}", userId);
        try {
            conn = pool.getConnection();

            UserResponse existingUser = userDao.getUserById(conn, userId);
            if (existingUser == null) {
                log.warn("User not found with id={}", userId);
                throw new Exception("User not found");
            }

            List<FineResponse> fines = userDao.getFinesByUserId(conn, userId);

            conn.commit();

            log.info("Fines found successfully with user id = {}", userId);

            return fines;
        } catch (Exception e) {
            log.error("View all fines by user id = {} failed: {}", userId, e.getMessage(), e);
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
