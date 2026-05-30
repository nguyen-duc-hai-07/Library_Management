package com.library.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class BorrowRequest {
    private int bookId;
    private int userId;
    private LocalDateTime dueDate;

    public BorrowRequest() {
    }
}
