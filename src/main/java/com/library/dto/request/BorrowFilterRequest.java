package com.library.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.library.model.BorrowStatus;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BorrowFilterRequest {
    private BorrowStatus status;
    private int page;
    private int size;

    public BorrowStatus getStatus() {
        return status;
    }
    public void setStatus(BorrowStatus status) {
        this.status = status;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
}
