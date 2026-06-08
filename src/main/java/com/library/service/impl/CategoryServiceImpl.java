package com.library.service.impl;

import com.library.dto.request.CategoryFilterRequest;
import com.library.dto.request.CategoryRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.CategoryResponse;
import com.library.exception.NotFoundException;
import com.library.model.Category;
import com.library.repository.CategoryRepository;
import com.library.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        log.info("create category");

        Category categories = new Category(
                request.getName()
        );

        Category saved = categoryRepository.save(categories);

        log.info("Category created successfully");

        return new CategoryResponse(
                saved.getId(),
                saved.getName()
        );
    }

    @Override
    public List<CategoryResponse> filter(CategoryFilterRequest filter) {
        log.info("View categories with filter: keyword={}, page={}, size={}",
                filter.getKeyword(),
                filter.getPage(),
                filter.getSize());

        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize());

        return categoryRepository.findWithFilter(filter.getKeyword(), pageable);
    }

    @Override
    public CategoryResponse viewCategoryById(int id) {
        log.info("View category with id={}", id);

        CategoryResponse categories = getCategoryResponseOrThrow(id);

        log.info("Category found successfully with id={}", id);

        return categories;
    }

    @Override
    public CategoryResponse updateCategory(int id, CategoryRequest request) {
        log.info("Update category with id = {}", id);

        Category category = categoryRepository.findEntityById(id)
                .orElseThrow(() -> {
                    log.warn("Category not found with id={}", id);
                    return new NotFoundException("Category not found");
                });

        category.setName(request.getName());

        Category saved = categoryRepository.save(category);

        log.info("Category updated successfully with id = {}", id);

        return new CategoryResponse(
                saved.getId(),
                saved.getName()
        );
    }

    @Override
    public void softDeleteCategory(int id) {
        log.info("Soft delete category with id={}", id);

        getCategoryResponseOrThrow(id);

        categoryRepository.softDelete(id);

        log.info("Category soft deleted successfully with id = {}", id);
    }

    @Override
    public List<BookResponse> viewAllBooksByCategory(int categoryId) {
        log.info("View all books by category with id={}", categoryId);

        getCategoryResponseOrThrow(categoryId);

        List<BookResponse> books = categoryRepository.findBooksByCategoryId(categoryId);

        log.info("Books found successfully with category id={} and total = {}", categoryId, books.size());

        return books;
    }

    private CategoryResponse getCategoryResponseOrThrow(int id) {
        return categoryRepository.findActiveById(id)
                .orElseThrow(() -> {
                    log.warn("Category not found with id={}", id);
                    return new NotFoundException("Category not found");
                });
    }

    public Category getAvailableCategoryOrThrow(int categoryId) {
        return categoryRepository.findEntityById(categoryId)
                .orElseThrow(() -> {
                    log.warn("Category not found with id={}", categoryId);
                    return new NotFoundException("Category not found");
                });
    }
}
