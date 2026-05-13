package com.library.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.library.model.User;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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

    public BookResponse() {
    }
    public BookResponse(int id, String title, String description, String isbn, String name, String publisher, String publishYear, int totalQuantity, int availableQuantity, String categoryName, String authorName, int authorId, int categoryId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.name = name;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.categoryName = categoryName;
        this.authorName = authorName;
        this.authorId = authorId;
        this.categoryId = categoryId;
    }
    public BookResponse(int id, String title, String description, String isbn, String name, String publisher, String publishYear, int totalQuantity, int availableQuantity, int authorId, int categoryId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.name = name;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.authorId = authorId;
        this.categoryId = categoryId;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public int getTotalQuantity() {
        return totalQuantity;
    }
    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    public int getAvailableQuantity() {
        return availableQuantity;
    }
    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
    public List<UserResponse> getUsers() {
        return users;
    }
    public void setUsers(List<UserResponse> users) {
        this.users = users;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public int getAuthorId() {
        return authorId;
    }
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public String getPublishYear() {
        return publishYear;
    }
    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }
}
