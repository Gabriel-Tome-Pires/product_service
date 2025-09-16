package com.example.product_service.repository;

import com.example.product_service.model.Category;
import com.example.product_service.model.Product;
import com.example.product_service.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);
    List<Product> findByCategory(Category category);
    List<Product> findByOwnerId(Long ownerId);
    List<Product> findByStatus(ProductStatus status);
    List<Product> findBySKUContaining(String sku);
}
