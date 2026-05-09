package com.library.dto.response;

import com.library.model.Book;
import java.util.List;
public class CategoryResponse {
    private int id;
    private String name;
    private List<BookResponse> books;
    public CategoryResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public CategoryResponse() {}
    public List<BookResponse> getBooks() {
        return books;
    }
    public void setBooks(List<BookResponse> books) {
        this.books = books;
    }
}
