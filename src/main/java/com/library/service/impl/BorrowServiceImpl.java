package com.library.service.impl;

import com.library.config.DBConnectionPool;
import com.library.dao.BookDao;
import com.library.dao.BorrowDao;
import com.library.dao.UserDao;
import com.library.dto.request.BookFilterRequest;
import com.library.dto.request.BorrowFilterRequest;
import com.library.dto.request.BorrowRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.BorrowResponse;
import com.library.dto.response.UserResponse;
import com.library.model.Borrow;
import com.library.service.BorrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Slf4j
@Service
public class BorrowServiceImpl implements BorrowService {
    private final DBConnectionPool pool = DBConnectionPool.getInstance();
    private final BorrowDao borrowDao;
    private final UserDao userDao;
    private final BookDao bookDao;
    public BorrowServiceImpl(BorrowDao borrowDao, UserDao userDao, BookDao bookDao) {
        this.userDao = userDao;
        this.borrowDao = borrowDao;
        this.bookDao = bookDao;
    }

    public BorrowResponse borrowBook(BorrowRequest borrowRequest) throws Exception {
        Connection conn = null;
        Borrow borrow = new Borrow(
                borrowRequest.getBookId(),
                borrowRequest.getUserId(),
                borrowRequest.getDueDate()
        );
        log.info("Borrow book");
        try {
            conn = pool.getConnection();

            UserResponse user = userDao.getUserById(conn, borrowRequest.getUserId());
            if (user == null) {
                log.warn("User not found with id={}", borrowRequest.getUserId());
                throw new Exception("User not found");
            }

            BookResponse book = bookDao.getBookById(conn, borrowRequest.getBookId());
            if (book == null) {
                log.warn("Book not found with id={}", borrowRequest.getBookId());
                throw new Exception("Book not found");
            }

            if(book.getAvailableQuantity() <= 0) {
                log.warn("Book is not available");
                throw new Exception("Book is not available");
            }

            borrowDao.insert(conn, borrow);
            bookDao.updateBorrowedQuantity(conn, borrowRequest.getBookId(), -1);

            conn.commit();

            log.info("Book borrowed successfully");

            return borrowDao.getBorrowById(conn, borrow.getId());
        } catch (Exception e) {
            log.error("Error borrowing book: {}", e.getMessage(), e);
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

    public List<BorrowResponse> viewBorrowsWithFilter(BorrowFilterRequest filter) throws Exception {
        Connection conn = null;
        log.info(
                "View borrows with filter: status={}, page={}, size={}",
                filter.getStatus(),
                filter.getPage(),
                filter.getSize()
        );
        try {
            conn = pool.getConnection();

            List<BorrowResponse> borrows = borrowDao.getBorrowsWithFilter(conn, filter);

            conn.commit();

            log.info("Borrows found successfully, total={}", borrows.size() );

            return borrows;


        } catch (Exception e) {
            log.error("Error viewing all borrows: {}", e.getMessage(), e);
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

    public BorrowResponse viewBorrowById(int id) throws Exception {
        Connection conn = null;
        log.info("View borrow with id = {}", id);
        try {
            conn = pool.getConnection();

            BorrowResponse borrow = borrowDao.getBorrowById(conn, id);
            if (borrow == null) {
                log.warn("Borrow not found with id={}", id);
                throw new Exception("Borrow not found");
            }

            conn.commit();

            log.info("Borrow found successfully");

            return borrow;

        } catch (Exception e) {
            log.error("Error viewing borrow with id = {}: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void deleteBorrow(int id) throws Exception {
        Connection conn = null;
        log.info("Delete borrow with id = {}", id);

        try {
            conn = pool.getConnection();

            BorrowResponse borrow = borrowDao.getBorrowById(conn, id);
            if (borrow == null) {
                log.warn("Borrow not found with id={}", id);
                throw new Exception("Borrow not found");
            }

            borrowDao.delete(conn, id);

            conn.commit();

            log.info("Borrow deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting borrow with id = {}: {}", id, e.getMessage(), e);
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

    public void softDeleteBorrow(int id) throws Exception {
        Connection conn = null;
        log.info("Soft delete borrow with id = {}", id);

        try {
            conn = pool.getConnection();

            BorrowResponse borrow = borrowDao.getBorrowById(conn, id);
            if (borrow == null) {
                log.warn("Borrow not found with id={}", id);
                throw new Exception("Borrow not found");
            }

            borrowDao.softDelete(conn, id);

            conn.commit();

            log.info("Borrow soft deleted successfully");
        } catch (Exception e) {
            log.error("Error soft deleting borrow with id = {}: {}", id, e.getMessage(), e);
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

    public BorrowResponse returnBook(int id) throws Exception {
        Connection conn = null;
        log.info("Return book with id = {}", id);

        try {
            conn = pool.getConnection();

            BorrowResponse borrow = borrowDao.getBorrowById(conn, id);
            if (borrow == null) {
                log.warn("Borrow not found with id={}", id);
                throw new Exception("Borrow not found");
            }

            bookDao.updateBorrowedQuantity(conn, borrow.getBookId(), 1);
            borrowDao.returnBook(conn, id);

            conn.commit();

            log.info("Book returned successfully");

            return borrowDao.getBorrowById(conn, id);
        } catch (Exception e) {
            log.error("Error returning book with id = {}: {}", id, e.getMessage(), e);
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
