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
    CategoryResponse createCategory(CategoryRequest request) throws Exception;

    List<CategoryResponse> viewCategoriesWithFilter(CategoryFilterRequest filter) throws Exception;

    CategoryResponse viewCategoryById(int id) throws Exception;

    CategoryResponse updateCategory(int id, CategoryRequest request) throws Exception;

    void deleteCategory(int id) throws Exception;

    void softDeleteCategory(int id) throws Exception;

    List<BookResponse> viewAllBooksByCategory(int categoryId) throws Exception;
}
