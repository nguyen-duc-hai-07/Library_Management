package com.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true) // toBuilder() giúp không tạo thêm object
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_deleted")
    @Builder.Default
    private boolean isDeleted = false;

    @Column(name = "isbn", unique = true, nullable = false)
    private String isbn;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "publish_year", nullable = false)
    private String publishYear;

    @Column(name = "total_quantity", nullable = false)
    @Builder.Default
    private int totalQuantity = 1;

    @Column(name = "available_quantity", nullable = false)
    @Builder.Default
    private int availableQuantity = 1;

    public Book(int id, String title, String description,
                String isbn, String name, String publisher,
                String publishYear, Author author, Category category
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.name = name;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.author = author;
        this.category = category;
    }
}
