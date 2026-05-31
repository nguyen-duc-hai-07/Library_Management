package com.library.service;

import com.library.dto.request.FineFilterRequest;
import com.library.dto.request.FineRequest;
import com.library.dto.response.FineResponse;
import com.library.model.Fine;

import java.math.BigDecimal;
import java.util.List;

public interface FineService {
    Fine createFineForLateReturn(Fine fine);

    FineResponse viewFineById(int id);

    FineResponse payFine(int id);

    void softDeleteFine(int id);

    List<FineResponse> filter(FineFilterRequest filter);

    boolean existsByBorrowId(int borrowId);

    FineResponse getFineResponseOrThrow(int id);

    void updateDaysLate(int id, int daysLate, BigDecimal fineAmount);
}