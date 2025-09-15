package com.example.product_service.repository;

import com.example.product_service.model.Product;
import com.example.product_service.model.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, Long> {
        ProductQuantity findByProduct(Product product);
}
