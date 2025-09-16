package com.example.product_service.service;

import com.example.product_service.exception.ObjectNotFoundException;
import com.example.product_service.model.Category;
import com.example.product_service.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory(Category category){
        validateCategory(category.getCategory());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id){
        getCategoryById(id);

        categoryRepository.deleteById(id);
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("A category with this id was not found"));
    }

    public Category updateCategory(Category category, Long id){
        validateCategory(category.getCategory());

        Category updatedCategory = getCategoryById(id);
        updatedCategory.setCategory(category.getCategory());
        return categoryRepository.save(category);
    }

    private void validateCategory(String categoryName){
        if(categoryName != null && !categoryName.isEmpty()){
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
    }
}
