package com.library.facade;

import com.library.dto.request.BorrowRequest;
import com.library.dto.response.BorrowResponse;

public interface BorrowFacadeService {
    BorrowResponse borrowBook(BorrowRequest request);

    BorrowResponse returnBook(int borrowId);
}
