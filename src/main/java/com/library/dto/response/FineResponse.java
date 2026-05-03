package com.library.dto.response;

import com.library.model.FineStatus;

import java.time.LocalDateTime;

public class FineResponse {
    private int id;
    private int userId;
    private int borrowId;
    private FineStatus status;
    private double fineAmount;
    private int daysLate;
    private LocalDateTime createdAt;

    public FineResponse() {
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
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
