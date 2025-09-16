package com.example.product_service.service;

import com.example.product_service.exception.ObjectNotFoundException;
import com.example.product_service.model.Product;
import com.example.product_service.model.ProductQuantity;
import com.example.product_service.repository.ProductQuantityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductQuantityService {
    private final ProductQuantityRepository productQuantityRepository;

    public List<ProductQuantity> getAllProductQuantity(){
        return productQuantityRepository.findAll();
    }

    public ProductQuantity getProductQuantityById(Long id){
        return productQuantityRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Product Quantity Not Found"));
    }

    public ProductQuantity findProductQuantityByProduct(Product product){
        return productQuantityRepository.findByProduct(product);
    }

    public ProductQuantity createProductQuantity(ProductQuantity productQuantity){
        //TODO check if is valid
        return productQuantityRepository.save(productQuantity);
    }

    public ProductQuantity updateProductQuantity(ProductQuantity productQuantity, Long id){
        //TODO check if is valid
        ProductQuantity productQuantityToUpdate = getProductQuantityById(id);
        productQuantityToUpdate.setQuantity(productQuantity.getQuantity());
        productQuantityToUpdate.setProduct(productQuantity.getProduct());
        return productQuantityRepository.save(productQuantityToUpdate);
    }

    public void deleteProductQuantityById(Long id){
        getProductQuantityById(id);
        productQuantityRepository.deleteById(id);
    }

}
