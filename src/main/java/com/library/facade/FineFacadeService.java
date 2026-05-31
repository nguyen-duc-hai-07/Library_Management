package com.library.facade;

import com.library.dto.request.FineFilterRequest;
import com.library.dto.request.FineRequest;
import com.library.dto.response.FineResponse;
import java.util.List;

public interface FineFacadeService {
    FineResponse createFineForLateReturn(FineRequest fineRequest);

    FineResponse viewFineById(int fineId);

    List<FineResponse> filter(FineFilterRequest filter);
}
