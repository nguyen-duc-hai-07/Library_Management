package com.library.service.impl;

import com.library.config.DBConnectionPool;
import com.library.dao.CategoryDao;
import com.library.dto.request.CategoryFilterRequest;
import com.library.dto.request.CategoryRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.CategoryResponse;
import com.library.model.Category;
import com.library.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;
    private final DBConnectionPool pool = DBConnectionPool.getInstance();

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public CategoryResponse createCategory(CategoryRequest request) throws Exception {
        Connection conn = null;

        Category category = new Category(request.getName());

        log.info("Create category");

        try {
            conn = pool.getConnection();

            categoryDao.insert(conn, category);

            conn.commit();

            log.info("Category created successfully with id = {}", category.getId());

            return new CategoryResponse(
                    category.getId(),
                    category.getName()
            );
        } catch (Exception e) {
            log.error("Create category failed: {}", e.getMessage(), e);
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

    public List<CategoryResponse> filter(CategoryFilterRequest filter) throws Exception {

        Connection conn = null;

        log.info(
                "View categories with filter: keyword={}, page={}, size={}",
                filter.getKeyword(),
                filter.getPage(),
                filter.getSize()
        );

        try {

            conn = pool.getConnection();

            List<CategoryResponse> categories =
                    categoryDao.getCategoriesWithFilter(
                            conn,
                            filter
                    );

            conn.commit();

            log.info(
                    "Categories found successfully, total={}",
                    categories.size()
            );

            return categories;

        } catch (Exception e) {

            log.error("View categories with filter failed: {}", e.getMessage(), e);

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

    public CategoryResponse viewCategoryById(int id) throws Exception {
        Connection conn = null;
        log.info("View category with id = {}", id);

        try {
            conn = pool.getConnection();

            CategoryResponse category = categoryDao.getCategoryById(conn, id);
            if (category == null) {
                log.warn("Category not found with id={}", id);
                throw new Exception("Category not found");
            }

            conn.commit();

            log.info("Category found successfully with id = {}", id);

            return category;
        } catch (Exception e) {
            log.error("View category by id = {} failed: {}", id, e.getMessage(), e);
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

    public CategoryResponse updateCategory(int id, CategoryRequest request) throws Exception {
        Connection conn = null;
        Category category = new Category(request.getName());
        log.info("Update category with id = {}", id);

        try {
            conn = pool.getConnection();

            CategoryResponse existingCategory = categoryDao.getCategoryById(conn, id);
            if (existingCategory == null) {
                log.warn("Category not found with id={}", id);
                throw new Exception("Category not found");
            }

            categoryDao.update(conn, category);

            conn.commit();

            log.info("Category updated successfully with id = {}", id);

            return new CategoryResponse(
                    category.getId(),
                    category.getName()
            );
        } catch (Exception e) {
            log.error("Update category by id = {} failed: {}", id, e.getMessage(), e);
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

    public void deleteCategory(int id) throws Exception {
        Connection conn = null;
        log.info("Delete category with id = {}", id);

        try {
            conn = pool.getConnection();

            CategoryResponse existingCategory = categoryDao.getCategoryById(conn, id);
            if (existingCategory == null) {
                log.warn("Category not found with id={}", id);
                throw new Exception("Category not found");
            }

            categoryDao.delete(conn, id);

            conn.commit();

            log.info("Category deleted successfully with id = {}", id);
        } catch (Exception e) {
            log.error("Delete category by id = {} failed: {}", id, e.getMessage(), e);
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

    public void softDeleteCategory(int id) throws Exception {
        Connection conn = null;
        log.info("Soft delete category with id = {}", id);

        try {
            conn = pool.getConnection();

            CategoryResponse existingCategory = categoryDao.getCategoryById(conn, id);
            if (existingCategory == null) {
                log.warn("Category not found with id={}", id);
                throw new Exception("Category not found");
            }

            categoryDao.softDelete(conn, id);

            conn.commit();

            log.info("Category soft deleted successfully with id = {}", id);
        } catch (Exception e) {
            log.error("Soft delete category by id = {} failed: {}", id, e.getMessage(), e);
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

    public List<BookResponse> viewAllBooksByCategory(int categoryId) throws Exception {
        Connection conn = null;
        log.info("View all books by category id = {}", categoryId);

        try {
            conn = pool.getConnection();

            CategoryResponse existingCategory = categoryDao.getCategoryById(conn, categoryId);
            if (existingCategory == null) {
                log.warn("Category not found with id={}", categoryId);
                throw new Exception("Category not found");
            }

            List<BookResponse> books = categoryDao.getBooksByCategoryId(conn, categoryId);

            conn.commit();

            log.info("Books found successfully with category id = {}", categoryId);

            return books;
        } catch (Exception e) {
            log.error("View all books by category id = {} failed: {}", categoryId, e.getMessage(), e);
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
