package com.library.controller;

import com.library.dto.request.FineFilterRequest;
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

    @PostMapping("/filter")
    public List<FineResponse> getAll(@RequestBody FineFilterRequest filter) throws Exception {
        log.info("view fines");

        log.debug(
                "fine filter request: status:{}, page:{}, size:{}",
                filter.getStatus(),
                filter.getPage(),
                filter.getSize()
        );

        return fineService.viewFinesWithFilter(filter);
    }

    @GetMapping("/{id}")
    public FineResponse getId(@PathVariable int id) throws Exception {
        log.info("view fine");

        log.debug("fine id={}", id);

        return fineService.viewFineById(id);
    }

    @PostMapping
    public FineResponse create(@RequestBody FineRequest fine) throws Exception {
        log.info("create fine");

        return fineService.createFineForLateReturn(fine);

    }


    @PatchMapping("/{id}")
    public void softDelete(@PathVariable int id) throws Exception {
        log.info("soft delete fine");

        log.debug("Soft delete id={}", id);

        fineService.softDeleteFine(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) throws Exception {
        log.info("delete fine");

        log.debug("Delete id={}", id);

        fineService.deleteFine(id);
    }

    @PatchMapping("/{id}/pay")
    public FineResponse payFine(@PathVariable int id) throws Exception {
        log.info("pay fine");

        log.debug("Pay fine id={}", id);

        return fineService.payFine(id);
    }
}
