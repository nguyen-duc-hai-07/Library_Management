package com.library.controller;

import com.library.dto.request.FineRequest;
import com.library.dto.response.FineResponse;
import com.library.service.FineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1/fines")
public class FineController {
    private final FineService fineService;
    public FineController(FineService fineService) {
        this.fineService = fineService;
    }

    @GetMapping
    public List<FineResponse> getAllFines() throws Exception {
        log.info("Get/api/v1/fines");
        return fineService.viewAllFines();
    }

    @GetMapping("/{id}")
    public FineResponse getFineById(@PathVariable int id) throws Exception {
        log.info("Get/api/v1/fines/{}",id);
        return fineService.viewFineById(id);
    }

    @PostMapping
    public FineResponse createFine(@RequestBody FineRequest fine) throws Exception {
        log.info("Post/api/v1/fines");
        return fineService.createFineForLateReturn(fine);
    }


    @PatchMapping("/{id}")
    public void softDeleteFine(@PathVariable int id) throws Exception {
        log.info("Patch/api/v1/fines/{}",id);
        fineService.softDeleteFine(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFine(@PathVariable int id) throws Exception {
        log.info("Delete/api/v1/fines/{}",id);
        fineService.deleteFine(id);
    }

    @PatchMapping("/{id}/pay")
    public FineResponse payFine(@PathVariable int id) throws Exception {
        log.info("Patch/api/v1/fines/{}/pay",id);
        return fineService.payFine(id);
    }
}
