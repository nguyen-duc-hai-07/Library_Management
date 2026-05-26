package com.library.service.impl;

import com.library.dto.request.FineFilterRequest;
import com.library.dto.request.FineRequest;
import com.library.dto.response.FineResponse;
import com.library.service.FineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FineServiceImpl implements FineService {
    @Override
    public FineResponse createFineForLateReturn(FineRequest fineRequest) throws Exception {
        return null;
    }

    @Override
    public FineResponse viewFineById(int id) throws Exception {
        return null;
    }

    @Override
    public void deleteFine(int id) throws Exception {

    }

    @Override
    public FineResponse payFine(int id) throws Exception {
        return null;
    }

    @Override
    public void softDeleteFine(int id) throws Exception {

    }

    @Override
    public List<FineResponse> filter(FineFilterRequest filter) throws Exception {
        return List.of();
    }
}
