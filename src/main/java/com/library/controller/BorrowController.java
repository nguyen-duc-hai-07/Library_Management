package com.library.controller;

import com.library.dto.request.BorrowFilterRequest;
import com.library.dto.request.BorrowRequest;
import com.library.dto.response.BorrowResponse;
import com.library.facade.BorrowFacadeService;
import com.library.service.BorrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/borrows")
public class BorrowController {
    private final BorrowService borrowService;
    private final BorrowFacadeService borrowFacadeService;

    public BorrowController(BorrowService borrowService, BorrowFacadeService borrowFacadeService) {
        this.borrowFacadeService = borrowFacadeService;
        this.borrowService = borrowService;
    }

    @PostMapping("/filter")
    public List<BorrowResponse> filter(@RequestBody BorrowFilterRequest filter) throws Exception {
        log.info("view borrows");

        log.debug(
                "Borrow filter request: status={}, page={}, size={}",
                filter.getStatus(),
                filter.getPage(),
                filter.getSize()
        );

        return borrowService.filter(filter);
    }

    @GetMapping("/{id}")
    public BorrowResponse getId(@PathVariable int id) throws Exception {
        log.info("view borrows");

        log.debug("Borrow id={}", id);

        return borrowService.viewBorrowById(id);
    }

    @PostMapping
    public BorrowResponse create(@RequestBody BorrowRequest borrow) throws Exception {
        log.info("create borrow");

        return borrowFacadeService.borrowBook(borrow);
    }

    @PatchMapping("/{id}")
    public void softDelete(@PathVariable int id) throws Exception {
        log.info("soft delete borrows");

        log.debug("Borrow id={}", id);

        borrowService.softDeleteBorrow(id);
    }

    @PatchMapping("/{id}/return")
    public BorrowResponse returnBook(@PathVariable int id) throws Exception {
        log.info("return book");

        log.debug("Borrow id={}", id);

        return borrowFacadeService.returnBook(id);
    }
}

