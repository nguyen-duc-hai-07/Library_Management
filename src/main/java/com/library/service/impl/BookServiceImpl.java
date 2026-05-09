package com.library.service.impl;

import com.library.config.DBConnectionPool;
import com.library.dao.BookDao;
import com.library.dto.request.BookRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.UserResponse;
import com.library.model.Book;
import com.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final DBConnectionPool pool = DBConnectionPool.getInstance();

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public BookResponse createBook(BookRequest bookRequest) throws Exception {
        Connection conn = null;
        Book book = new Book(
                bookRequest.getAuthorId(),
                bookRequest.getCategoryId(),
                bookRequest.getTitle(),
                bookRequest.getDescription(),
                bookRequest.getIsbn(),
                bookRequest.getName(),
                bookRequest.getPublisher(),
                bookRequest.getPublishYear()
        );
        log.info("Create book");
        try {
            conn = pool.getConnection();

            bookDao.insert(conn, book);

            conn.commit();

            log.info("Book created successfully with id = {}", book.getId());

            return bookDao.getBookById(conn, book.getId());
        } catch (Exception e) {
            log.error("Error creating book: {}", e.getMessage());
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

    public BookResponse viewBookById(int id) throws Exception {
        Connection conn = null;
        log.info("View book by id = {}", id);
        try {
            conn = pool.getConnection();

            BookResponse book = bookDao.getBookById(conn, id);

            conn.commit();

            log.info("Book found successfully with id = {}", id);

            return book;

        } catch (Exception e) {
            log.error("Error viewing book: {}", e.getMessage());
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

    public List<BookResponse> viewAllBooks() throws Exception {
        Connection conn = null;
        log.info("View all books");
        try {
            conn = pool.getConnection();

            List<BookResponse> books = bookDao.getAllBooks(conn);

            conn.commit();

            log.info("All books found successfully");

            return books;
        } catch (Exception e) {
            log.error("Error viewing all books: {}", e.getMessage());
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

    public void softDeleteBook(int id) throws Exception {
        Connection conn = null;
        log.info("Soft delete book by id = {}", id);
        try {
            conn = pool.getConnection();

            BookResponse existingBook = bookDao.getBookById(conn, id);
            if (existingBook == null) {
                log.warn("Book not found with id={}", id);
                throw new Exception("Book not found");
            }

            bookDao.softDelete(conn, id);

            conn.commit();

            log.info("Book soft deleted successfully with id = {}", id);
        } catch (Exception e) {
            log.error("Error soft deleting book: {}", e.getMessage());
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

    public BookResponse updateBook(int id, BookRequest bookRequest) throws Exception {
        Connection conn = null;
        Book book = new Book(
                bookRequest.getAuthorId(),
                bookRequest.getCategoryId(),
                bookRequest.getTitle(),
                bookRequest.getDescription(),
                bookRequest.getIsbn(),
                bookRequest.getName(),
                bookRequest.getPublisher(),
                bookRequest.getPublishYear()
        );
        book.setId(id);
        log.info("Update book by id = {}", id);
        try {
            conn = pool.getConnection();

            BookResponse existingBook = bookDao.getBookById(conn, id);
            if (existingBook == null) {
                log.warn("Book not found with id={}", id);
                throw new Exception("Book not found");
            }

            bookDao.update(conn, book);

            conn.commit();

            log.info("Book updated successfully with id = {}", id);

            return bookDao.getBookById(conn, id);
        } catch (Exception e) {
            log.error("Update book by id = {} failed: {}", id, e.getMessage());
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

    public void deleteBook(int id) throws Exception {
        Connection conn = null;
        log.info("Delete book by id = {}", id);
        try {
            conn = pool.getConnection();

            BookResponse existingBook = bookDao.getBookById(conn, id);
            if (existingBook == null) {
                log.warn("Book not found with id={}", id);
                throw new Exception("Book not found");
            }
            bookDao.delete(conn, id);

            conn.commit();

            log.info("Book deleted successfully with id = {}", id);
        } catch (Exception e) {
            log.error("Delete book by id = {} failed: {}", id, e.getMessage());
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

    public List<UserResponse> viewAllUsersByBook(int bookId) throws Exception {
        Connection conn = null;
        log.info("View all users by book with id = {}", bookId);

        try {
            conn = pool.getConnection();

            BookResponse existingBook = bookDao.getBookById(conn, bookId);
            if (existingBook == null) {
                log.warn("Book not found with id={}", bookId);
                throw new Exception("Book not found");
            }

            List<UserResponse> users = bookDao.getUsersByBookId(conn, bookId);

            conn.commit();

            log.info("All users found successfully with book id = {}", bookId);

            return users;
        } catch (Exception e) {
            log.error("Error viewing all users by book: {}", e.getMessage());
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

    public BookResponse updateQuantity(int id, int quantity) throws Exception {
        Connection conn = null;
        log.info("Update quantity of book with id = {}", id);

        try {
            conn = pool.getConnection();

            BookResponse existingBook = bookDao.getBookById(conn, id);
            if (existingBook == null) {
                log.warn("Book not found with id={}", id);
            }
            bookDao.updateQuantity(conn, id, quantity);

            conn.commit();

            log.info("Quantity updated successfully with book id = {}", id);

            return bookDao.getBookById(conn, id);
        } catch (Exception e) {
            log.error("Error updating quantity: {}", e.getMessage());
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
