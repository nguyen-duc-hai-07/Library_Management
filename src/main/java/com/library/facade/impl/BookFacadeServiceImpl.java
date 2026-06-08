package com.library.facade.impl;

import com.library.dto.request.BookRequest;
import com.library.dto.response.BookResponse;
import com.library.facade.BookFacadeService;
import com.library.model.Author;
import com.library.model.Book;
import com.library.model.Category;
import com.library.service.AuthorService;
import com.library.service.BookService;
import com.library.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookFacadeServiceImpl implements BookFacadeService {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public BookResponse createBook(BookRequest bookRequest) {
        log.info("Create book");

        Category category = categoryService.getAvailableCategoryOrThrow(bookRequest.getCategoryId());

        Author author = authorService.getAvailableAuthorOrThrow(bookRequest.getAuthorId());

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

        Book savedBook = bookService.createBook(book);

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

    public BookResponse updateBook(int bookId, BookRequest bookRequest) {
        log.info("Update book");

        Category category = categoryService.getAvailableCategoryOrThrow(bookRequest.getCategoryId());

        Author author = authorService.getAvailableAuthorOrThrow(bookRequest.getAuthorId());

        Book updatedBook = new Book(
                bookRequest.getTitle(),
                bookRequest.getDescription(),
                bookRequest.getIsbn(),
                bookRequest.getName(),
                bookRequest.getPublisher(),
                bookRequest.getPublishYear(),
                author,
                category
        );
        updatedBook.setId(bookId);

        Book saved = bookService.updateBook(bookId, updatedBook);

        log.info("Book updated successfully with id = {}", bookId);

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
}
