package com.library.service.impl;

import com.library.dto.request.FineFilterRequest;
import com.library.dto.request.FineRequest;
import com.library.dto.response.FineResponse;
import com.library.model.Borrow;
import com.library.model.BorrowStatus;
import com.library.model.Fine;
import com.library.model.FineStatus;
import com.library.repository.BorrowRepository;
import com.library.repository.FineRepository;
import com.library.service.FineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@Transactional
public class FineServiceImpl implements FineService {
    private final FineRepository fineRepository;
    private final BorrowRepository borrowRepository;

    public FineServiceImpl(FineRepository fineRepository, BorrowRepository borrowRepository) {
        this.fineRepository = fineRepository;
        this.borrowRepository = borrowRepository;
    }

    @Override
    public FineResponse createFineForLateReturn(FineRequest fineRequest) {
        log.info("create fine");

        if(fineRepository.existsByBorrowId(fineRequest.getBorrowId())){
            log.warn("Fine already exists for borrowId={}", fineRequest.getBorrowId());
            throw new RuntimeException("Fine already exists");
        }

        Borrow borrow = borrowRepository.findEntityById(fineRequest.getBorrowId())
                .orElseThrow(() -> {
                    log.warn("Borrow not found with borrowId={}", fineRequest.getBorrowId());
                    return new RuntimeException("Borrow not found");
                });

        if(borrow.getStatus() == BorrowStatus.RETURNED){
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

        Fine savedFine = fineRepository.save(fine);

        log.info("Fine created successfully with id={}", savedFine.getId());

        return getFineResponseOrThrow(savedFine.getId());
    }

    @Override
    public FineResponse viewFineById(int id) {
        log.info("view fine with id={}", id);

        FineResponse fine = getFineResponseOrThrow(id);

        Borrow borrow = borrowRepository.findEntityById(fine.getBorrowId())
                .orElseThrow(() -> {
                    log.warn("Borrow not found with borrowId={}", fine.getBorrowId());
                    return new RuntimeException("Borrow not found");
                });

        int daysLate = (int) ChronoUnit.DAYS.between(borrow.getDueDate().toLocalDate(), LocalDate.now());
        if (daysLate <= 0) {
            log.warn("Borrow is not late");
            throw new RuntimeException("Borrow is not late");
        }

        BigDecimal fineAmount = BigDecimal.valueOf(daysLate * 10000);

        if (daysLate != fine.getDaysLate() && fine.getStatus() != FineStatus.PAID) {
            fine.setDaysLate(daysLate);

            fine.setFineAmount(fineAmount);

            fineRepository.updateDaysLate(id, daysLate, fineAmount);

            log.info("Fine updated successfully with id={}", id);
        }

        return fine;
    }

    @Override
    public FineResponse payFine(int id) {
        log.info("pay fine with id={}", id);

        getFineResponseOrThrow(id);

        fineRepository.payFine(id);

        log.info("Fine paid successfully with id={}", id);

        return getFineResponseOrThrow(id);
    }

    @Override
    public void softDeleteFine(int id) {
        log.info("soft delete fine with id={}", id);

        getFineResponseOrThrow(id);

        fineRepository.softDelete(id);

        log.info("Fine soft deleted successfully with id={}", id);

    }

    @Override
    public List<FineResponse> filter(FineFilterRequest filter) {
        log.info("View fines with filter: status = {}, page = {}, size = {}",
                filter.getStatus(),
                filter.getPage(),
                filter.getSize()
        );

        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize());

        List<FineResponse> fines = fineRepository.findWithFilter(filter.getStatus(), pageable);

        for (FineResponse fine : fines) {
            Borrow borrow = borrowRepository.findEntityById(fine.getBorrowId())
                    .orElseThrow(() -> {
                        log.warn("Borrow not found with borrowId={}", fine.getBorrowId());
                        return new RuntimeException("Borrow not found");
                    });

            int daysLate = (int) ChronoUnit.DAYS.between(borrow.getDueDate().toLocalDate(), LocalDate.now());
            if (daysLate <= 0) {
                log.warn("Borrow is not late");
                throw new RuntimeException("Borrow is not late");
            }

            BigDecimal fineAmount = BigDecimal.valueOf(daysLate * 10000);

            if (daysLate != fine.getDaysLate() && fine.getStatus() != FineStatus.PAID) {
                fine.setDaysLate(daysLate);

                fine.setFineAmount(fineAmount);

                fineRepository.updateDaysLate(fine.getId(), daysLate, fineAmount);
            }
        }

        log.info("fines found successfully, total= {}", fines.size());

        return fines;
    }

    private FineResponse getFineResponseOrThrow(int id) {
        return fineRepository.findActiveById(id)
                .orElseThrow(() -> {
                    log.warn("Fine not found with id={}", id);
                    return new RuntimeException("Fine not found");
                });
    }
}
