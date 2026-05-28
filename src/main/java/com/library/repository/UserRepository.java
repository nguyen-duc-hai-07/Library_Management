package com.library.repository;

import com.library.dto.response.BookResponse;
import com.library.dto.response.FineResponse;
import com.library.dto.response.UserResponse;
import com.library.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("""
            SELECT new com.library.dto.response.UserResponse(
                        u.id,
                        u.fullName,
                        u.email,
                        u.phoneNumber,
                        u.role,
                        u.status,
                        u.createdAt
                        )
            FROM User u
            WHERE u.id = :id 
            AND u.isDeleted = false
            """)
    Optional<UserResponse> findActiveById(@Param("id") int id);

    Optional<User> findEntityById(int id);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = :id AND u.isDeleted = false")
    void softDelete(@Param("id") int id);

    @Query("""
            SELECT new com.library.dto.response.BookResponse(
                        b.id, b.title, b.description, b.isbn, b.name, b.publisher, b.publishYear,
                        b.totalQuantity, b.availableQuantity, a.name, c.name, b.author.id, b.category.id
                        )
            FROM Book b
            JOIN b.category c
            JOIN b.author a
            JOIN Borrow br ON br.book.id = b.id
            WHERE br.user.id = :userId
            AND b.isDeleted = false
            AND a.isDeleted = false
            AND c.isDeleted = false
            AND br.isDeleted = false
            """)
    List<BookResponse> findBooksByUserId(@Param("userId") int userId);

    @Query("""
            SELECT new com.library.dto.response.FineResponse(
                        f.id,
                        f.user.id,
                        f.borrow.id,
                        f.status,
                        f.daysLate,
                        f.fineAmount,
                        f.paidAt
                        )
            FROM Fine f
            WHERE f.user.id = :userId
            AND f.isDeleted = false
            """)
    List<FineResponse> findFinesByUserId(@Param("userId") int userId);

    @Query("""
            SELECT new com.library.dto.response.UserResponse(
                        u.id,
                        u.fullName,
                        u.email,
                        u.phoneNumber,
                        u.role,
                        u.status,
                        u.createdAt
                        )
            FROM User u
            WHERE u.isDeleted = false
            AND (u.fullName ILIKE %:keyword% OR u.email ILIKE %:keyword% )
            """)
    List<UserResponse> findWithFilter(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isDeleted = false")
    Optional<User> findByEmail(@Param("email") String email);
}
