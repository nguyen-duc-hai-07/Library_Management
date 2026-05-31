package com.library.repository;

import com.library.dto.response.FineResponse;
import com.library.model.Fine;
import com.library.model.FineStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FineRepository extends JpaRepository<Fine, Integer> {
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
            WHERE f.id = :id
            AND f.isDeleted = false
            """)
    Optional<FineResponse> findActiveById(@Param("id") int id);

    Optional<Fine> findEntityById(int id);

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
            WHERE f.isDeleted
            AND f.status = :status
            """)
    List<FineResponse> findWithFilter(@Param("status") FineStatus status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Fine f SET f.isDeleted = true WHERE f.id = :id AND f.isDeleted = false")
    void softDelete(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("UPDATE Fine f SET f.status = 'PAID' WHERE f.id = :id AND f.isDeleted = false")
    void payFine(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Fine f SET f.daysLate = :daysLate, f.fineAmount = :fineAmount
            WHERE f.id = :id AND f.isDeleted = false
            """)
    void updateDaysLate(@Param("id") int id, @Param("daysLate") int daysLate, @Param("fineAmount") BigDecimal fineAmount);

    boolean existsByBorrowId(int borrowId);
}
