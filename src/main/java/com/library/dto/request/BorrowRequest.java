package com.library.dto.request;

import java.time.LocalDateTime;

public class BorrowRequest {
    private int bookId;
    private int userId;
    private LocalDateTime dueDate;

    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    public BorrowRequest() {
    }
}
