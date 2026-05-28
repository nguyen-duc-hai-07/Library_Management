package com.library.service;

import com.library.dto.request.BorrowFilterRequest;
import com.library.dto.request.BorrowRequest;
import com.library.dto.response.BorrowResponse;

import java.util.List;

public interface BorrowService {
    BorrowResponse borrowBook(BorrowRequest borrowRequest);

    List<BorrowResponse> filter(BorrowFilterRequest filter);

    BorrowResponse viewBorrowById(int id);

    void softDeleteBorrow(int id);

    BorrowResponse returnBook(int id);
}
