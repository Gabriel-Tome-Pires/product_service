package com.example.product_service.service;

import com.example.product_service.exception.ObjectNotFoundException;
import com.example.product_service.model.Product;
import com.example.product_service.model.ProductQuantity;
import com.example.product_service.repository.ProductQuantityRepository;
import com.example.product_service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductQuantityService {
    private final ProductQuantityRepository productQuantityRepository;
    private final ProductRepository productRepository;

    public ProductQuantity createProductQuantity(ProductQuantity productQuantity){
        checkIsValid(productQuantity);

        return productQuantityRepository.save(productQuantity);
    }

    public ProductQuantity updateProductQuantity(ProductQuantity productQuantity, Long id){
        checkIsValid(productQuantity);
        ProductQuantity productQuantityToUpdate = getProductQuantityById(id);

        productQuantityToUpdate.setQuantity(productQuantity.getQuantity());

        return productQuantityRepository.save(productQuantityToUpdate);
    }

    public void deleteProductQuantityById(Long id){
        getProductQuantityById(id);
        productQuantityRepository.deleteById(id);
    }

    public List<ProductQuantity> getAllProductQuantity(){
        return productQuantityRepository.findAll();
    }

    public ProductQuantity getProductQuantityById(Long id){
        return productQuantityRepository.findById(id)
                .orElseThrow(()-> new ObjectNotFoundException("Product Quantity with id "+id+" was Not Found"));
    }

    public ProductQuantity getProductQuantityByProduct(Product product){
        checkProductIsValid(product);

        return productQuantityRepository.findByProduct(product)
                .orElseThrow(()-> new ObjectNotFoundException("Product Quantity for the product "+product.getName()+
                                                              " with id "+product.getId()+" was Not Found"));
    }

    private void checkIsValid(ProductQuantity productQuantity){
        checkProductIsValid(productQuantity.getProduct());
        if(productQuantity.getQuantity() < 0){
            throw new IllegalArgumentException("Quantity cannot be less than 0");
        }
    }

    private void checkProductIsValid(Product product){
        Long id=product.getId();
        if(!productRepository.existsById(id)){
            throw new ObjectNotFoundException("Product with id "+id+" was not found");
        }
    }

}
