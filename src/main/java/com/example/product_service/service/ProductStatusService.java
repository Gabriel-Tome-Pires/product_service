package com.example.product_service.service;

import com.example.product_service.exception.ObjectCannotBeDeleted;
import com.example.product_service.exception.ObjectNotFoundException;
import com.example.product_service.model.ProductStatus;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.repository.ProductStatusRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.errors.DuplicateResourceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductStatusService {
    private final ProductStatusRepository productStatusRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ProductStatus createProductStatus(ProductStatus productStatus) {
        String validStatus=validateProductStatus(productStatus.getName());
        productStatus.setName(validStatus);

        return productStatusRepository.save(productStatus);
    }

    @Transactional
    public ProductStatus updateProductStatus(ProductStatus productStatus, Long id) {
        String validStatus=validateProductStatus(productStatus.getName());
        ProductStatus updateProductStatus=getProductStatusById(id);

        updateProductStatus.setName(validStatus);

        return productStatusRepository.save(updateProductStatus);
    }

    @Transactional
    public void deleteProductStatus(Long id) {
        ProductStatus status =getProductStatusById(id);
        if(productRepository.existsByStatus(status)) {
            throw new ObjectCannotBeDeleted
                    ("Product status with id "+id+" and name "+status.getName()+" is used by at least one product and cannot be deleted");
        }
        productStatusRepository.deleteById(id);
    }

    public List<ProductStatus> getAllProductStatuses(){
        return productStatusRepository.findAll();
    }

    public ProductStatus getProductStatusById(Long id){
        return productStatusRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Product status with id "+id+" was not found"));
    }

    private String validateProductStatus(String status){
        String validatedStatus=status.toLowerCase().trim();

        List<ProductStatus> productStatuses=getAllProductStatuses();
        for(ProductStatus productStatus:productStatuses){
            if(productStatus.getName().equals(validatedStatus)){
                throw new DuplicateResourceException("Product status with the name "+status+" already exists");
            }
        }

        return validatedStatus;
    }
}
