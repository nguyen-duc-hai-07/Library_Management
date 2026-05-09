package com.library.service;

import com.library.dto.request.BorrowRequest;
import com.library.dto.response.BorrowResponse;

import java.util.List;

public interface BorrowService {
    BorrowResponse borrowBook(BorrowRequest borrowRequest) throws Exception;

    List<BorrowResponse> viewAllBorrows() throws Exception;

    BorrowResponse viewBorrowById(int id) throws Exception;

    void deleteBorrow(int id) throws Exception;

    void softDeleteBorrow(int id) throws Exception;

    BorrowResponse returnBook(int id) throws Exception;
}
