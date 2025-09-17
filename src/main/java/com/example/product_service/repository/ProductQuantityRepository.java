package com.example.product_service.repository;

import com.example.product_service.model.Product;
import com.example.product_service.model.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, Long> {
        Optional<ProductQuantity> findByProduct(Product product);
}
