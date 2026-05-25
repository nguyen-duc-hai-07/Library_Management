package com.library.service.impl;

import com.library.dto.request.CategoryFilterRequest;
import com.library.dto.request.CategoryRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.CategoryResponse;
import com.library.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Override
    public CategoryResponse createCategory(CategoryRequest request) throws Exception {
        return null;
    }

    @Override
    public List<CategoryResponse> filter(CategoryFilterRequest filter) throws Exception {
        return List.of();
    }

    @Override
    public CategoryResponse viewCategoryById(int id) throws Exception {
        return null;
    }

    @Override
    public CategoryResponse updateCategory(int id, CategoryRequest request) throws Exception {
        return null;
    }

    @Override
    public void deleteCategory(int id) throws Exception {

    }

    @Override
    public void softDeleteCategory(int id) throws Exception {

    }

    @Override
    public List<BookResponse> viewAllBooksByCategory(int categoryId) throws Exception {
        return List.of();
    }
}
