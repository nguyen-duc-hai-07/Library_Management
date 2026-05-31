package com.library.facade.impl;

import com.library.dto.request.BorrowRequest;
import com.library.dto.response.BorrowResponse;
import com.library.facade.BorrowFacadeService;
import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.BorrowService;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BorrowFacadeServiceImpl implements BorrowFacadeService {
    private final BorrowService borrowService;
    private final BookService bookService;
    private final UserService userService;

    @Transactional
    public BorrowResponse borrowBook(BorrowRequest borrowRequest) {
        log.info("borrow book");

        Book book = bookService.getAvailableBookOrThrow(borrowRequest.getBookId());

        User user = userService.getAvailableUserOrThrow(borrowRequest.getUserId());

        Borrow borrow = Borrow.builder()
                .book(book)
                .user(user)
                .dueDate(borrowRequest.getDueDate())
                .build();

        Borrow savedBorrow = borrowService.borrowBook(borrow);

        bookService.updateQuantity(borrowRequest.getBookId(), -1);

        log.info("borrow book successfully with id = {}", savedBorrow.getId());

        return BorrowResponse.builder()
                .id(savedBorrow.getId())
                .bookId(savedBorrow.getBook().getId())
                .userId(savedBorrow.getUser().getId())
                .borrowDate(savedBorrow.getBorrowDate())
                .dueDate(savedBorrow.getDueDate())
                .returnDate(savedBorrow.getReturnDate())
                .status(savedBorrow.getStatus())
                .build();
    }

    @Transactional
    public BorrowResponse returnBook(int borrowId) {
        log.info("return book");

        BorrowResponse borrow = borrowService.returnBook(borrowId);

        bookService.updateStock(borrow.getBookId(), 1);

        log.info("return book successfully with id = {}", borrowId);

        return borrow;
    }
}
