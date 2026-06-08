package com.library.service.impl;

import com.library.dto.request.FineFilterRequest;
import com.library.dto.response.FineResponse;
import com.library.exception.NotFoundException;
import com.library.model.Borrow;
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

    public FineServiceImpl(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    @Override
    public Fine createFineForLateReturn(Fine fine) {

        log.info("create fine");

        return fineRepository.save(fine);
    }


    @Override
    public FineResponse viewFineById(int id) {
        log.info("view fine with id={}", id);

        return getFineResponseOrThrow(id);
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

        List<FineResponse> fineResponse = fineRepository.findWithFilter(filter.getStatus(), pageable);

        log.info("fines found successfully, total= {}", fineResponse.size());

        return fineResponse;
    }

    public FineResponse getFineResponseOrThrow(int id) {
        return fineRepository.findActiveById(id)
                .orElseThrow(() -> {
                    log.warn("Fine not found with id={}", id);
                    return new NotFoundException("Fine not found");
                });
    }

    public boolean existsByBorrowId(int borrowId) {
        return fineRepository.existsByBorrowId(borrowId);
    }

    public void updateDaysLate(int id, int daysLate, BigDecimal fineAmount) {
        log.info("Update days late and fine amount of fine with id={}", id);

        fineRepository.updateDaysLate(id, daysLate, fineAmount);
    }
}
