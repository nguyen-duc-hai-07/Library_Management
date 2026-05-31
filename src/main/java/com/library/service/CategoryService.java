package com.library.service;

import com.library.dto.request.CategoryFilterRequest;
import com.library.dto.request.CategoryRequest;
import com.library.dto.request.UserRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.CategoryResponse;
import com.library.dto.response.UserResponse;
import com.library.model.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    List<CategoryResponse> filter(CategoryFilterRequest filter);

    CategoryResponse viewCategoryById(int id);

    CategoryResponse updateCategory(int id, CategoryRequest request);

    void softDeleteCategory(int id) ;

    List<BookResponse> viewAllBooksByCategory(int categoryId) ;

    Category getAvailableCategoryOrThrow(int categoryId);
}
