package com.library.controller;

import com.library.dto.response.BookResponse;
import com.library.dto.response.CategoryResponse;
import com.library.dto.request.CategoryRequest;

import com.library.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() throws Exception {
        log.info("Get/api/v1/categories");
        return categoryService.viewAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(int id) throws Exception {
        log.info("Get/api/v1/categories/{}",id);
        return categoryService.viewCategoryById(id);
    }

    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest request) throws Exception {
        log.info("Post/api/v1/categories");
        return categoryService.createCategory(request);
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable int id, @RequestBody CategoryRequest request) throws Exception {
        log.info("Put/api/v1/categories/{}",id);
        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable int id) throws Exception {
        log.info("Delete/api/v1/categories/{}",id);
        categoryService.deleteCategory(id);
    }

    @PatchMapping("/{id}")
    public void softDeleteCategory(@PathVariable int id) throws Exception {
        log.info("Patch/api/v1/categories/{}",id);
        categoryService.softDeleteCategory(id);
    }

    @GetMapping("/{id}/books")
    public List<BookResponse> viewAllBooksByCategory(@PathVariable int id) throws Exception {
        log.info("Get/api/v1/categories/{}/books",id);
        return categoryService.viewAllBooksByCategory(id);
    }
}
