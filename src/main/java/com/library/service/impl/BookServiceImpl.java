package com.library.service.impl;

import com.library.dto.request.BookFilterRequest;
import com.library.dto.request.BookRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.UserResponse;
import com.library.model.Author;
import com.library.model.Book;
import com.library.model.Category;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;
import com.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository, AuthorRepository authorRepository) {
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public BookResponse createBook(BookRequest bookRequest) throws Exception {
        log.info("Create book");

        Author author = authorRepository.findEntityById(bookRequest.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Category category = categoryRepository.findEntityById(bookRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .description(bookRequest.getDescription())
                .isbn(bookRequest.getIsbn())
                .name(bookRequest.getName())
                .publisher(bookRequest.getPublisher())
                .publishYear(bookRequest.getPublishYear())
                .author(author)
                .category(category)
                .build();

        Book savedBook = bookRepository.save(book);

        log.info("Book created successfully with id={}", savedBook.getId());

        return BookResponse.builder()
                .id(savedBook.getId())
                .title(savedBook.getTitle())
                .description(savedBook.getDescription())
                .isbn(savedBook.getIsbn())
                .name(savedBook.getName())
                .publisher(savedBook.getPublisher())
                .publishYear(savedBook.getPublishYear())
                .authorId(savedBook.getAuthor().getId())
                .categoryId(savedBook.getCategory().getId())
                .authorName(savedBook.getAuthor().getName())
                .categoryName(savedBook.getCategory().getName())
                .totalQuantity(savedBook.getTotalQuantity())
                .availableQuantity(savedBook.getAvailableQuantity())
                .build();
    }

    @Override
    public List<BookResponse> filter(BookFilterRequest filter) throws Exception {
        log.info("View books with filter: keyword={}, page={}, size={}",
                filter.getKeyword(),
                filter.getPage(),
                filter.getSize());

        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize());

        return bookRepository.findWithFilter(filter.getKeyword(), pageable);
    }

    @Override
    public BookResponse viewBookById(int id) throws Exception {
        log.info("View book with id={}", id);

        BookResponse books = getBookResponseOrThrow(id); //hàm check id tồn tại

        log.info("Book found successfully with id={}", id);

        return books;
    }

    @Override
    public BookResponse updateBook(int id, BookRequest bookRequest) throws Exception {
        log.info("Update book with id={}", id);

        Book books = getBookOrThrow(id);

        Author author = authorRepository.findEntityById(bookRequest.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Category category = categoryRepository.findEntityById(bookRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Book updatedBook = new Book(
                books.getId(),
                bookRequest.getTitle(),
                bookRequest.getDescription(),
                bookRequest.getIsbn(),
                bookRequest.getName(),
                bookRequest.getPublisher(),
                bookRequest.getPublishYear(),
                author,
                category
        );

        Book saved = bookRepository.save(updatedBook);

        log.info("Book updated successfully with id = {}", id);

        return BookResponse.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .isbn(saved.getIsbn())
                .name(saved.getName())
                .description(saved.getDescription())
                .publisher(saved.getPublisher())
                .publishYear(saved.getPublishYear())
                .authorId(saved.getAuthor().getId())
                .categoryId(saved.getCategory().getId())
                .authorName(saved.getAuthor().getName())
                .categoryName(saved.getCategory().getName())
                .totalQuantity(saved.getTotalQuantity())
                .availableQuantity(saved.getAvailableQuantity())
                .build();
    }

    @Override
    public void softDeleteBook(int id) throws Exception {
        log.info("Soft delete book with id={}", id);

        getBookOrThrow(id);

        bookRepository.softDelete(id);

        log.info("Book soft deleted successfully with id = {}", id);
    }

    @Override
    public List<UserResponse> viewAllUsersByBook(int bookId) throws Exception {
        log.info("View all users with bookId = {}", bookId);

        getBookResponseOrThrow(bookId);

        List<UserResponse> users = bookRepository.findUsersByBookId(bookId);

        log.info("Users found successfully with book id={}", bookId);

        return users;
    }

    @Override
    public BookResponse updateQuantity(int id, int quantity) throws Exception {
        log.info("Update quantity of book with id={}", id);

        getBookOrThrow(id);

        bookRepository.updateAvailableQuantity(id, quantity);

        log.info("Quantity updated successfully with id={}", id);

        return getBookResponseOrThrow(id);
    }

    private Book getBookOrThrow(int id) {
        return bookRepository.findEntityById(id)
                .orElseThrow(() -> {
                    log.warn("Book not found with id={}", id);
                    return new RuntimeException("Book not found");
                });
    }

    private BookResponse getBookResponseOrThrow(int id) {
        return bookRepository.findActiveBookById(id)
                .orElseThrow(() -> {
                    log.warn("Book not found with id={}", id);
                    return new RuntimeException("Book not found");
                });
    }
}
