package com.library.facade.impl;

import com.library.dto.request.FineFilterRequest;
import com.library.dto.request.FineRequest;
import com.library.dto.response.FineResponse;
import com.library.facade.FineFacadeService;
import com.library.model.Borrow;
import com.library.model.BorrowStatus;
import com.library.model.Fine;
import com.library.model.FineStatus;
import com.library.service.BorrowService;
import com.library.service.FineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FineFacadeServiceImpl implements FineFacadeService {
    private final FineService fineService;
    private final BorrowService borrowService;

    @Transactional
    public FineResponse createFineForLateReturn(FineRequest fineRequest) {
        log.info("create fine for late return");

        if (fineService.existsByBorrowId(fineRequest.getBorrowId())) {
            log.warn("Fine already exists with borrow id = {}", fineRequest.getBorrowId());
            throw new RuntimeException("Fine already exists");
        }

        Borrow borrow = borrowService.getAvailableBorrowOrThrow(fineRequest.getBorrowId());

        if (borrow.getStatus() == BorrowStatus.RETURNED) {
            log.warn("Borrow is already returned");
            throw new RuntimeException("Borrow is already returned");
        }

        int daysLate = (int) ChronoUnit.DAYS.between(borrow.getDueDate().toLocalDate(), LocalDate.now());
        if (daysLate <= 0) {
            log.warn("Borrow is not late");
            throw new RuntimeException("Borrow is not late");
        }

        BigDecimal fineAmount = BigDecimal.valueOf(daysLate * 10000L);

        Fine fine = Fine.builder()
                .borrow(borrow)
                .user(borrow.getUser())
                .daysLate(daysLate)
                .fineAmount(fineAmount)
                .build();

        Fine savedFine = fineService.createFineForLateReturn(fine);

        log.info("Fine created successfully with id = {}", savedFine.getId());

        return FineResponse.builder()
                .id(savedFine.getId())
                .borrowId(savedFine.getBorrow().getId())
                .userId(savedFine.getUser().getId())
                .daysLate(savedFine.getDaysLate())
                .fineAmount(savedFine.getFineAmount())
                .status(savedFine.getStatus())
                .paidAt(savedFine.getPaidAt())
                .build();
    }

    @Transactional
    public FineResponse viewFineById(int fineId) {
        log.info("view fine with id={}", fineId);

        FineResponse fineResponse = fineService.getFineResponseOrThrow(fineId);

        Borrow borrow = borrowService.getAvailableBorrowOrThrow(fineResponse.getBorrowId());

        int daysLate = (int) ChronoUnit.DAYS.between(borrow.getDueDate().toLocalDate(), LocalDate.now());
        if (daysLate <= 0) {
            log.warn("Borrow is not late");
            throw new RuntimeException("Borrow is not late");
        }

        BigDecimal fineAmount = BigDecimal.valueOf(daysLate * 10000);
        if (daysLate != fineResponse.getDaysLate() && fineResponse.getStatus() != FineStatus.PAID) {
            fineResponse.setFineAmount(fineAmount);

            fineResponse.setDaysLate(daysLate);

            fineService.updateDaysLate(fineId, daysLate, fineAmount);

            log.info("Fine updated successfully with id = {}", fineId);
        }

        return fineResponse;
    }

    @Transactional
    public List<FineResponse> filter(FineFilterRequest filter) {
        log.info("View fines with filter: status = {}, page = {}, size = {}",
                filter.getStatus(),
                filter.getPage(),
                filter.getSize()
        );

        List<FineResponse> fineResponse = fineService.filter(filter);

        for (FineResponse fine : fineResponse) {
            Borrow borrow = borrowService.getAvailableBorrowOrThrow(fine.getBorrowId());

            int daysLate = (int) ChronoUnit.DAYS.between(borrow.getDueDate().toLocalDate(), LocalDate.now());
            if (daysLate <= 0) {
                log.warn("Borrow is not late");
                throw new RuntimeException("Borrow is not late");
            }

            BigDecimal fineAmount = BigDecimal.valueOf(daysLate * 10000);

            if (daysLate != fine.getDaysLate() && fine.getStatus() != FineStatus.PAID) {
                fine.setFineAmount(fineAmount);

                fine.setDaysLate(daysLate);

                fineService.updateDaysLate(fine.getId(), daysLate, fineAmount);

                log.info("Fine updated successfully with id = {}", fine.getId());
            }
        }

        return fineResponse;
    }
}
