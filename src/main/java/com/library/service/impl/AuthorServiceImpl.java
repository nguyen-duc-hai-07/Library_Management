package com.library.service.impl;

import com.library.config.DBConnectionPool;
import com.library.dao.AuthorDao;
import com.library.dto.request.AuthorFilterRequest;
import com.library.dto.request.AuthorRequest;
import com.library.dto.response.AuthorResponse;
import com.library.dto.response.BookResponse;
import com.library.model.Author;
import com.library.service.AuthorService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    private final DBConnectionPool pool = DBConnectionPool.getInstance();

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public AuthorResponse createAuthor(AuthorRequest request) throws Exception {

        Connection conn = null;

        Author author = new Author(request.getName(),request.getYear(),request.getDescription());

        log.info("Create author");

        try {

            conn = pool.getConnection();

            authorDao.insert(conn, author);

            conn.commit();

            log.info("Author created successfully with id = {}", author.getId());

            return new AuthorResponse(
                    author.getId(),
                    author.getName(),
                    author.getYear(),
                    author.getDescription()
                    );

        } catch (Exception e) {

            log.error("Create author failed: {}", e.getMessage(), e);

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

    public List<AuthorResponse> viewAuthorsWithFilter(AuthorFilterRequest filter) throws Exception {

        Connection conn = null;

        log.info(
                "View authors with filter: keyword={}, page={}, size={}",
                filter.getKeyword(),
                filter.getPage(),
                filter.getSize()
        );


        try {

            conn = pool.getConnection();

            List<AuthorResponse> author = authorDao.getAuthorsWithFilter(conn,filter);

            conn.commit();

            log.info("Authors found successfully, total={}", author.size());

            return author;

        } catch (Exception e) {

            log.error("View authors with filter failed: {}", e.getMessage(), e);

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

    public AuthorResponse viewAuthorById(int id) throws Exception {

        Connection conn = null;

        log.info("View author with id = {}", id);

        try {

            conn = pool.getConnection();

            AuthorResponse author = authorDao.getAuthorById(conn, id);
            if (author == null) {
                log.warn("Author not found with id={}", id);
                throw new Exception("Author not found");
            }

            conn.commit();

            log.info("Author found successfully with id = {}", id);

            return author;

        } catch (Exception e) {

            log.error("View author by id = {} failed: {}", id, e.getMessage(), e);

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

    public AuthorResponse updateAuthor(int id, AuthorRequest request) throws Exception {

        Connection conn = null;

        Author author = new Author(
                request.getName(),
                request.getYear(),
                request.getDescription()
        );

        author.setId(id);

        log.info("Update author with id = {}", id);

        try {

            conn = pool.getConnection();

            AuthorResponse existingAuthor = authorDao.getAuthorById(conn, id);

            if (existingAuthor == null) {

                log.warn("Author not found with id={}", id);

                throw new Exception("Author not found");
            }

            authorDao.update(conn, author);

            conn.commit();

            log.info("Author updated successfully with id = {}", id);

            return new AuthorResponse(
                    author.getId(),
                    author.getName(),
                    author.getYear(),
                    author.getDescription()
            );

        } catch (Exception e) {

            log.error("Update author by id = {} failed: {}", id, e.getMessage(), e);

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

    public void deleteAuthor(int id) throws Exception {

        Connection conn = null;

        log.info("Delete author with id = {}", id);

        try {

            conn = pool.getConnection();

            AuthorResponse existingAuthor = authorDao.getAuthorById(conn, id);

            if (existingAuthor == null) {

                log.warn("Author not found with id={}", id);

                throw new Exception("Author not found");
            }

            authorDao.delete(conn, id);

            conn.commit();

            log.info("Author deleted successfully with id = {}", id);

        } catch (Exception e) {

            log.error("Delete author by id = {} failed: {}", id, e.getMessage(), e);

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

    public void softDeleteAuthor(int id) throws Exception {

        Connection conn = null;

        log.info("Soft delete author with id = {}", id);

        try {

            conn = pool.getConnection();

            AuthorResponse existingAuthor = authorDao.getAuthorById(conn, id);

            if (existingAuthor == null) {

                log.warn("Author not found with id={}", id);
                throw new Exception("Author not found");

            }

            authorDao.softDelete(conn, id);

            conn.commit();

            log.info("Author soft deleted successfully with id = {}", id);

        } catch (Exception e) {

            log.error("Soft delete author by id = {} failed: {}", id, e.getMessage(), e);

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

    public List<BookResponse> viewAllBooksByAuthor(int authorId) throws Exception {

        Connection conn = null;

        log.info("View all books by author id = {}", authorId);

        try {

            conn = pool.getConnection();

            AuthorResponse existingAuthor = authorDao.getAuthorById(conn, authorId);

            if (existingAuthor == null) {

                log.warn("Author not found with id={}", authorId);

                throw new Exception("Author not found");
            }

            List<BookResponse> books = authorDao.getBooksByAuthorId(conn, authorId);

            conn.commit();

            log.info("Books found successfully with author id = {}", authorId);

            return books;

        } catch (Exception e) {

            log.error("View all books by author id = {} failed: {}", authorId, e.getMessage(), e);

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