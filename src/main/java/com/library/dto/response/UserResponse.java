package com.library.dto.response;

import com.library.model.Book;
import com.library.model.UserRole;
import com.library.model.UserStatus;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponse {
    private int id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime createdAt;
    private List<BookResponse> books;

    public UserResponse() {
        this.createdAt = LocalDateTime.now();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
    public UserStatus getStatus() {
        return status;
    }
    public void setStatus(UserStatus status) {
        this.status = status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public List<BookResponse> getBooks() {
        return books;
    }
    public void setBooks(List<BookResponse> books) {
        this.books = books;
    }
}
