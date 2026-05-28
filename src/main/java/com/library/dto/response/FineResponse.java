package com.library.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.library.model.FineStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FineResponse {
    private int id;
    private int userId;
    private int borrowId;
    private FineStatus status;
    private BigDecimal fineAmount;
    private int daysLate;
    private LocalDateTime paidAt;

    public FineResponse(
            int id, int userId, int borrowId,
            FineStatus status, int daysLate, BigDecimal fineAmount,
            LocalDateTime paidAt
    ) {
        this.id = id;
        this.userId = userId;
        this.borrowId = borrowId;
        this.status = status;
        this.daysLate = daysLate;
        this.fineAmount = fineAmount;
        this.paidAt = paidAt;
    }
}
