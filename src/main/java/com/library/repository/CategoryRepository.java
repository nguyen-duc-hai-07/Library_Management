package com.library.repository;

import com.library.dto.response.BookResponse;
import com.library.dto.response.CategoryResponse;
import com.library.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("""
            SELECT new com.library.dto.response.CategoryResponse(
                        c.id,
                        c.name
                        )
            FROM Category c 
            WHERE c.id = :id
            AND c.isDeleted = false
            """)
    Optional<CategoryResponse> findActiveById(@Param("id") int id);

    Optional<Category> findEntityById(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.isDeleted = true WHERE c.id = :id AND c.isDeleted = false")
    void softDelete(@Param("id") int id);

    @Query("""
            SELECT new com.library.dto.response.BookResponse(
                       b.id,
                       b.title,
                       b.isbn,
                       b.author.id,
                       a.name,
                       b.category.id,
                       c.name
                                  )
            FROM Book b
            JOIN b.author a 
            JOIN b.category c
            WHERE b.category.id = :categoryId
            AND b.isDeleted = false
            AND a.isDeleted = false
            AND c.isDeleted = false
            """)
    List<BookResponse> findBooksByCategoryId(@Param("categoryId") int categoryId);

    @Query("""
            SELECT new com.library.dto.response.CategoryResponse(
                        c.id,
                        c.name
                        )
            FROM Category c 
            WHERE c.isDeleted = false
            AND c.name ILIKE %:keyword%
            """)
    List<CategoryResponse> findWithFilter(@Param("keyword") String keyword, Pageable pageable);
}
