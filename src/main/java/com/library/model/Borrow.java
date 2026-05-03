package com.library.model;

import java.time.LocalDateTime;

public class Borrow {
    private int id;
    private int bookId;
    private int userId;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private LocalDateTime dueDate;
    private boolean isDeleted;
    private BorrowStatus status;

    public Borrow() {
        this.borrowDate = LocalDateTime.now();
        this.isDeleted = false;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
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
    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }
    public void setBorrowDate(LocalDateTime borrowDate) {
        this.borrowDate = borrowDate;
    }
    public LocalDateTime getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
    public BorrowStatus getStatus() {
        return status;
    }
    public void setStatus(BorrowStatus status) {
        this.status = status;
    }
}
