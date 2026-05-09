package com.library.model;

import java.time.LocalDateTime;

public class Fine {
    private int id;
    private int userId;
    private int borrowId;
    private FineStatus status;
    private double fineAmount;
    private int daysLate;
    private LocalDateTime paidAt;
    private boolean isDeleted;

    public Fine(int userId, int borrowId,  double fineAmount, int daysLate) {
        this.userId = userId;
        this.borrowId = borrowId;
        this.fineAmount = fineAmount;
        this.daysLate = daysLate;
    }
    public Fine() {
        this.isDeleted = false;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getBorrowId() {
        return borrowId;
    }
    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }
    public FineStatus getStatus() {
        return status;
    }
    public void setStatus(FineStatus status) {
        this.status = status;
    }
    public double getFineAmount() {
        return fineAmount;
    }
    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }
    public int getDaysLate() {
        return daysLate;
    }
    public void setDaysLate(int daysLate) {
        this.daysLate = daysLate;
    }
    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
    public LocalDateTime getPaidAt() {
        return paidAt;
    }
    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }
}
