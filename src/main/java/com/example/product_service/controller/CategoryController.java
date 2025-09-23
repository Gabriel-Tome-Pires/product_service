package com.example.product_service.controller;

import com.example.product_service.model.Category;
import com.example.product_service.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
      Category newCategory =categoryService.createCategory(category);
      return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category,@PathVariable Long id) {
        Category updatedCategory=categoryService.updateCategory(category, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        Category category=categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategory(){
        List<Category> categories=categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

}
