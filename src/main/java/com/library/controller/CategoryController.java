package com.library.controller;

import com.library.dto.request.CategoryFilterRequest;
import com.library.dto.request.CategoryRequest;
import com.library.dto.response.BookResponse;
import com.library.dto.response.CategoryResponse;
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

    @PostMapping("/filter")
    public List<CategoryResponse> filter(@RequestBody CategoryFilterRequest filter) throws Exception {
        log.info("view Categories");

        log.debug(
                "Category filter request: keyword : {}, page : {}, size : {}",
                filter.getKeyword(),
                filter.getPage(),
                filter.getSize()
        );

        return categoryService.filter(filter);
    }

    @GetMapping("/{id}")
    public CategoryResponse getId(@PathVariable int id) throws Exception {
        log.info("view category");

        log.debug("Category id={}", id);

        return categoryService.viewCategoryById(id);
    }

    @PostMapping
    public CategoryResponse create(@RequestBody CategoryRequest request) throws Exception {
        log.info("create category");

        return categoryService.createCategory(request);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable int id, @RequestBody CategoryRequest request) throws Exception {
        log.info("update category");

        log.debug("Update request: id={}", id);

        return categoryService.updateCategory(id, request);
    }

    @PatchMapping("/{id}")
    public void softDelete(@PathVariable int id) throws Exception {
        log.info("soft delete category");

        log.debug("Soft delete id={}", id);

        categoryService.softDeleteCategory(id);
    }

    @GetMapping("/{id}/books")
    public List<BookResponse> viewBooks(@PathVariable int id) throws Exception {
        log.info("view books by category");

        log.debug("Category id={}", id);

        return categoryService.viewAllBooksByCategory(id);
    }
}
