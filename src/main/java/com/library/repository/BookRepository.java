package com.library.repository;

import com.library.dto.response.BookResponse;
import com.library.dto.response.UserResponse;
import com.library.model.Book;
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
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("""
            SELECT new com.library.dto.response.BookResponse(
                        b.id,b.title,b.description,b.isbn,b.name,b.publisher,b.publishYear,
                        b.totalQuantity,b.availableQuantity,a.name,c.name,b.author.id,b.category.id
                        )
            FROM Book b
            JOIN b.author a
            JOIN b.category c
            WHERE b.id = :id
            AND b.isDeleted = false
            AND a.isDeleted = false
            AND c.isDeleted = false
            """)
    Optional<BookResponse> findActiveBookById(@Param("id") int id);

    Optional<Book> findEntityById(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Book b SET b.isDeleted = true WHERE b.id = :id AND b.isDeleted = false")
    void softDelete(@Param("id") int id);

    @Transactional
    @Modifying
    @Query(""" 
           UPDATE Book b SET b.availableQuantity = b.availableQuantity + :quantity,
           b.totalQuantity = b.totalQuantity + :quantity
           WHERE b.id = :id AND b.isDeleted = false
                      """)
    void updateAvailableQuantity(@Param("id") int id, @Param("quantity") int quantity);

    @Transactional
    @Modifying
    @Query("""
           UPDATE Book b SET b.availableQuantity = b.availableQuantity + :quantity
           WHERE b.id = :id AND b.isDeleted = false
           """)
    void updateQuantity(@Param("id") int id, @Param("quantity") int quantity);

    @Query("""
    SELECT new com.library.dto.response.UserResponse (
        u.id, u.fullName, u.email, u.phoneNumber
        )
    FROM User u
    JOIN Borrow b ON u.id = b.user.id
    WHERE b.book.id = :id
    AND b.isDeleted = false
""")
    List<UserResponse> findUsersByBookId(@Param("id") int id);

    @Query("""
            SELECT new com.library.dto.response.BookResponse(
                        b.id,b.title,b.description,b.isbn,b.name,b.publisher,b.publishYear,
                        b.totalQuantity,b.availableQuantity,a.name,c.name,b.author.id,b.category.id
                        )
            FROM Book b
            JOIN b.author a
            JOIN b.category c
            WHERE b.isDeleted = false
            AND a.isDeleted = false
            AND c.isDeleted = false
            AND (b.title ILIKE %:keyword% OR b.isbn ILIKE %:keyword%)
            """)
    List<BookResponse> findWithFilter(@Param("keyword") String keyword, Pageable pageable);
}
