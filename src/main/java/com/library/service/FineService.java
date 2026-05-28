package com.library.service;

import com.library.dto.request.FineFilterRequest;
import com.library.dto.request.FineRequest;
import com.library.dto.response.FineResponse;

import java.util.List;

public interface FineService {
    FineResponse createFineForLateReturn(FineRequest fineRequest);

    FineResponse viewFineById(int id);

    FineResponse payFine(int id);

    void softDeleteFine(int id);

    List<FineResponse> filter(FineFilterRequest filter);
}