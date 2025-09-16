package com.example.product_service.service;

import com.example.product_service.exception.ObjectNotFoundException;
import com.example.product_service.model.Product;
import com.example.product_service.model.ProductStatus;
import com.example.product_service.repository.ProductStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductStatusService {
    private final ProductStatusRepository productStatusRepository;

    public ProductStatus createProductStatus(ProductStatus productStatus) {
        validateProductStatus(productStatus.getStatus());
        return productStatusRepository.save(productStatus);
    }

    public List<ProductStatus> getAllProductStatuses(){
        return productStatusRepository.findAll();
    }

    public ProductStatus getProductStatusById(Long id){
        return productStatusRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Product status with this id was not found"));
    }

    public ProductStatus UpdateProductStatus(ProductStatus productStatus, Long id) {
        validateProductStatus(productStatus.getStatus());
        ProductStatus updateProductStatus=getProductStatusById(id);
        updateProductStatus.setStatus(productStatus.getStatus());
        return productStatusRepository.save(updateProductStatus);
    }

    public void deleteProductStatus(Long id) {
        getProductStatusById(id);
        productStatusRepository.deleteById(id);
    }

    private void validateProductStatus(String status){
        if(status==null || status.isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
    }
}
