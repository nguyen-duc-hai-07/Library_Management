package com.library.dto.request;

public class FineRequest {
    private int borrowId;
    private int userId;
    private double fineAmount;
    private int daysLate;

    public int getBorrowId() {
        return borrowId;
    }
    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
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
    public FineRequest() {
    }
}
