package com.library.controller;

import com.library.dto.request.BorrowRequest;
import com.library.dto.response.BorrowResponse;
import com.library.service.BorrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/borrows")
public class BorrowController {
    private final BorrowService borrowService;
    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @GetMapping
    public List<BorrowResponse> getAllBorrows() throws Exception {
        log.info("Get/api/v1/borrows");
        return borrowService.viewAllBorrows();
    }

    @GetMapping("/{id}")
    public BorrowResponse getBorrowById(@PathVariable int id) throws Exception {
        log.info("Get/api/v1/borrows/{}",id);
        return borrowService.viewBorrowById(id);
    }

    @PostMapping
    public BorrowResponse createBorrow(@RequestBody BorrowRequest borrow) throws Exception {
        log.info("Post/api/v1/borrows");
        return borrowService.borrowBook(borrow);
    }

    @DeleteMapping("/{id}")
    public void deleteBorrow(@PathVariable int id) throws Exception {
        log.info("Delete/api/v1/borrows/{}",id);
        borrowService.deleteBorrow(id);
    }

    @PatchMapping("/{id}")
    public void softDeleteBorrow(@PathVariable int id) throws Exception {
        log.info("Patch/api/v1/borrows/{}",id);
        borrowService.softDeleteBorrow(id);
    }

    @PatchMapping("/{id}/return")
    public BorrowResponse returnBook(@PathVariable int id) throws Exception {
        log.info("Patch /api/v1/borrows/{}/return", id);
        return borrowService.returnBook(id);
    }
}

