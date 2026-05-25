package com.library.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String title;
    private String description;
    private String isbn;
    private String name;
    private String publisher;
    private String publishYear;
    private int authorId;
    private int categoryId;
}
