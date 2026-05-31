package com.library.service;

import com.library.dto.request.BorrowFilterRequest;
import com.library.dto.response.BorrowResponse;
import com.library.model.Borrow;

import java.util.List;

public interface BorrowService {
    Borrow borrowBook(Borrow borrow);

    List<BorrowResponse> filter(BorrowFilterRequest filter);

    BorrowResponse viewBorrowById(int id);

    void softDeleteBorrow(int id);

    BorrowResponse returnBook(int id);

    Borrow getAvailableBorrowOrThrow(int borrowId);
}
