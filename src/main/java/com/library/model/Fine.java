package com.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fines")
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_id", nullable = false)
    private Borrow borrow;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "fine_status")
    private FineStatus status;

    @Column(name = "fine_amount", nullable = false)
    private BigDecimal fineAmount;

    @Column(name = "days_late", nullable = false)
    private int daysLate;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "is_deleted")
    @Builder.Default
    private boolean isDeleted = false;

}
