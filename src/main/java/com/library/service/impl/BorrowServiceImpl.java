package com.library.service.impl;

import com.library.dto.request.BorrowFilterRequest;
import com.library.dto.request.BorrowRequest;
import com.library.dto.response.BorrowResponse;
import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.User;
import com.library.repository.BookRepository;
import com.library.repository.BorrowRepository;
import com.library.repository.UserRepository;
import com.library.service.BorrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class BorrowServiceImpl implements BorrowService {
    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BorrowServiceImpl(
            BorrowRepository borrowRepository,
            BookRepository bookRepository,
            UserRepository userRepository
    ) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BorrowResponse borrowBook(BorrowRequest borrowRequest) {
        log.info("borrow book");

        Book book = bookRepository.findEntityById(borrowRequest.getBookId())
                .orElseThrow(() -> {
                    log.warn("Book not found with bookId={}", borrowRequest.getBookId());
                    return new RuntimeException("Book not found");
                });

        if (book.getAvailableQuantity() <= 0) {
            throw new RuntimeException("Book is out of stock");
        }

        User user = userRepository.findEntityById(borrowRequest.getUserId())
                .orElseThrow(() -> {
                    log.warn("User not found with userId={}", borrowRequest.getUserId());
                    return new RuntimeException("User not found");
                });

        Borrow borrow = Borrow.builder()
                .book(book)
                .user(user)
                .dueDate(borrowRequest.getDueDate())
                .build();

        Borrow savedBorrow = borrowRepository.save(borrow);

        bookRepository.updateQuantity(savedBorrow.getBook().getId(), -1);

        log.info("borrow book successfully with id={}", savedBorrow.getId());

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

    @Override
    public List<BorrowResponse> filter(BorrowFilterRequest filter) {
        log.info("View borrows with filter: status = {}, page = {} , size = {}",
                filter.getStatus(),
                filter.getPage(),
                filter.getSize()
        );

        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize());

        List<BorrowResponse> borrows = borrowRepository.findWithFilter(filter.getStatus(), pageable);

        log.debug("Found {} borrows", borrows.size());

        return borrows;
    }

    @Override
    public BorrowResponse viewBorrowById(int id)  {
        log.info("view borrow with id = {}", id);

        BorrowResponse borrows = getBorrowResponseOrThrow(id);

        log.info("borrow found successfully with id = {}", id);

        return borrows;
    }

    @Override
    public void softDeleteBorrow(int id) {
        log.info("soft delete borrow with id = {}", id);

        getBorrowResponseOrThrow(id);

        borrowRepository.softDelete(id);

        log.info("borrow soft deleted successfully with id = {}", id);
    }

    @Override
    public BorrowResponse returnBook(int id) {
        log.info("return book with id = {}", id);

        BorrowResponse borrow = getBorrowResponseOrThrow(id);

        borrowRepository.returnBook(id);

        bookRepository.updateQuantity(borrow.getBookId(), 1);

        log.info("borrow returned successfully with id = {}", id);

        return getBorrowResponseOrThrow(id);
    }

    private BorrowResponse getBorrowResponseOrThrow(int id) {
        return borrowRepository.findActiveById(id)
                .orElseThrow(() -> {
                    log.warn("Borrow not found with id = {}", id);
                    return new RuntimeException("Borrow not found");
                });
    }
}
