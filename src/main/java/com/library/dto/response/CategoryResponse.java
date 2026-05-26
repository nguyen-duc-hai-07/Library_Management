package com.library.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.library.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private int id;
    private String name;
    private List<BookResponse> books;
    private boolean isDeleted;

    public CategoryResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
