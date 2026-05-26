package com.library.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookResponse {
    private int id;
    private String title;
    private String description;
    private String isbn;
    private String name;
    private String publisher;
    private String publishYear;
    private int totalQuantity;
    private int availableQuantity;
    private String categoryName;
    private String authorName;
    private int authorId;
    private int categoryId;
    private List<UserResponse> users;

    public BookResponse(int id, String title, String isbn,
                        int authorId, String authorName,
                        int categoryId, String categoryName) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.authorId = authorId;
        this.authorName = authorName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public BookResponse(int id, String title, String description, String isbn,
                        String name, String publisher, String publishYear,
                        int totalQuantity, int availableQuantity,
                        String authorName, String categoryName,
                        int authorId, int categoryId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.name = name;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.authorName = authorName;
        this.categoryName = categoryName;
        this.authorId = authorId;
        this.categoryId = categoryId;
    }
}
