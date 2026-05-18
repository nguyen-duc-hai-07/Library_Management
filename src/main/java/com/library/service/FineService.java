package com.library.service;

import com.library.dto.request.FineFilterRequest;
import com.library.dto.request.FineRequest;
import com.library.dto.response.FineResponse;

import java.util.List;

public interface FineService {
    FineResponse createFineForLateReturn(FineRequest fineRequest) throws Exception;

    FineResponse viewFineById(int id) throws Exception;

    void deleteFine(int id) throws Exception;

    FineResponse payFine(int id) throws Exception;

    void softDeleteFine(int id) throws Exception;

    List<FineResponse> filter(FineFilterRequest filter) throws Exception;
}