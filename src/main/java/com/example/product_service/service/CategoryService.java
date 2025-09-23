package com.example.product_service.service;

import com.example.product_service.exception.ObjectCannotBeDeleted;
import com.example.product_service.exception.ObjectNotFoundException;
import com.example.product_service.model.Category;
import com.example.product_service.repository.CategoryRepository;
import com.example.product_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.errors.DuplicateResourceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Category createCategory(Category category){
        String validatedCategory=validateCategory(category.getName());
        category.setName(validatedCategory);

        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id){
        Category category=getCategoryById(id);
        if(productRepository.existsByCategory(category)){
            throw new ObjectCannotBeDeleted
                    ("Product category with id "+id+" and name "+category.getName()+" is in use by at least one product and cannot be deleted");
        }

        categoryRepository.deleteById(id);
    }

    @Transactional
    public Category updateCategory(Category category, Long id){
        String validatedCategory=validateCategory(category.getName());
        Category updatedCategory = getCategoryById(id);

        updatedCategory.setName(validatedCategory);

        return categoryRepository.save(updatedCategory);
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("A category with this id was not found"));
    }

    private String validateCategory(String categoryName){
        String validatedCategory=categoryName.toLowerCase().trim();
        List<Category> categories=getAllCategories();
        for(Category category:categories){
            if(category.getName().equals(validatedCategory)){
                throw new DuplicateResourceException("A category with the name "+validatedCategory+" already exists");
            }
        }
        return validatedCategory;
    }
}
