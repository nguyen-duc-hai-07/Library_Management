package com.library.service.impl;

import com.library.dto.request.BorrowFilterRequest;
import com.library.dto.request.BorrowRequest;
import com.library.dto.response.BorrowResponse;
import com.library.service.BorrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BorrowServiceImpl implements BorrowService {

    @Override
    public BorrowResponse borrowBook(BorrowRequest borrowRequest) throws Exception {
        return null;
    }

    @Override
    public List<BorrowResponse> filter(BorrowFilterRequest filter) throws Exception {
        return List.of();
    }

    @Override
    public BorrowResponse viewBorrowById(int id) throws Exception {
        return null;
    }

    @Override
    public void deleteBorrow(int id) throws Exception {

    }

    @Override
    public void softDeleteBorrow(int id) throws Exception {

    }

    @Override
    public BorrowResponse returnBook(int id) throws Exception {
        return null;
    }
}
