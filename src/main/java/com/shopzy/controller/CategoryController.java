package com.shopzy.controller;

import com.shopzy.model.Category;
import com.shopzy.service.impl.CategoryServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryServiceImpl categoryServiceImpl;

    public CategoryController(CategoryServiceImpl categoryServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryServiceImpl.createCategory(category);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryServiceImpl.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryServiceImpl.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryServiceImpl.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryServiceImpl.deleteCategory(id);
        return "Category deleted successfully";
    }
}