package com.library.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorResponse {

    private int id;
    private String name;
    private int year;
    private String description;
    private List<BookResponse> books;

    @JsonIgnore
    private boolean isDeleted;

    public AuthorResponse(int id, String name, int year, String description) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.description = description;
    }
}