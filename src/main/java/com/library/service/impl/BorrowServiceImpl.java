package com.library.service.impl;

import com.library.dto.request.BorrowFilterRequest;
import com.library.dto.response.BorrowResponse;
import com.library.model.Borrow;
import com.library.repository.BorrowRepository;
import com.library.service.BorrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class BorrowServiceImpl implements BorrowService {
    private final BorrowRepository borrowRepository;

    public BorrowServiceImpl(
            BorrowRepository borrowRepository
            ) {
        this.borrowRepository = borrowRepository;
    }

    @Override
    public Borrow borrowBook(Borrow borrow) {
        log.info("borrow book");

        return borrowRepository.save(borrow);
    }

    @Override
    public List<BorrowResponse> filter(BorrowFilterRequest filter) {
        log.info("View borrows with filter: status = {}, page = {} , size = {}",
                filter.getStatus(),
                filter.getPage(),
                filter.getSize()
        );

        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize());

        List<BorrowResponse> borrows = borrowRepository.findWithFilter(filter.getStatus(), pageable);

        log.debug("Found {} borrows", borrows.size());

        return borrows;
    }

    @Override
    public BorrowResponse viewBorrowById(int id) {
        log.info("view borrow with id = {}", id);

        BorrowResponse borrows = getBorrowResponseOrThrow(id);

        log.info("borrow found successfully with id = {}", id);

        return borrows;
    }

    @Override
    public void softDeleteBorrow(int id) {
        log.info("soft delete borrow with id = {}", id);

        getBorrowResponseOrThrow(id);

        borrowRepository.softDelete(id);

        log.info("borrow soft deleted successfully with id = {}", id);
    }

    @Override
    public BorrowResponse returnBook(int id) {
        log.info("return book with id = {}", id);

        borrowRepository.returnBook(id);

        BorrowResponse borrow = getBorrowResponseOrThrow(id);

        log.info("borrow returned successfully with id = {}", id);

        return borrow;
    }

    private BorrowResponse getBorrowResponseOrThrow(int id) {
        return borrowRepository.findActiveById(id)
                .orElseThrow(() -> {
                    log.warn("Borrow not found with id = {}", id);
                    return new RuntimeException("Borrow not found");
                });
    }

    public Borrow getAvailableBorrowOrThrow(int borrowId) {
        return borrowRepository.findEntityById(borrowId)
                .orElseThrow(() -> {
                    log.warn("Borrow not found with id = {}", borrowId);
                    return new RuntimeException("Borrow not found");
                });
    }
}
