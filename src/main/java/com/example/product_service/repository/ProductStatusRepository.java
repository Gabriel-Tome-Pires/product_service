package com.example.product_service.repository;

import com.example.product_service.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStatusRepository extends JpaRepository<ProductStatus, Long> {

}
