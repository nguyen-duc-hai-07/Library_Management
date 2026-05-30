package com.library.repository;

import com.library.dto.response.BorrowResponse;
import com.library.model.Borrow;
import com.library.model.BorrowStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {
    @Query("""
            SELECT new com.library.dto.response.BorrowResponse(
                        br.id,
                        br.user.id,
                        br.book.id,
                        br.borrowDate,
                        br.returnDate,
                        br.dueDate,
                        br.status
                        )
            FROM Borrow br
            WHERE br.id = :id
            AND br.isDeleted = false
            """)
    Optional<BorrowResponse> findActiveById(@Param("id") int id);

    Optional<Borrow> findEntityById(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Borrow br SET br.isDeleted = true WHERE br.id = :id AND br.isDeleted = false")
    void softDelete(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("UPDATE Borrow br SET br.status = 'RETURNED', br.returnDate = CURRENT_TIMESTAMP WHERE br.id = :id AND br.isDeleted = false")
    void returnBook(@Param("id") int id);

    @Query("""
            SELECT new com.library.dto.response.BorrowResponse(
                        br.id,
                        br.user.id,
                        br.book.id,
                        br.borrowDate,
                        br.returnDate,
                        br.dueDate,
                        br.status
                        )
            FROM Borrow br
            WHERE br.isDeleted = false
            AND br.status = :status
            """)
    List<BorrowResponse> findWithFilter(@Param("status")BorrowStatus status, Pageable pageable);
}
