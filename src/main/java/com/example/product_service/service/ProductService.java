package com.example.product_service.service;

import com.example.product_service.exception.ObjectNotFoundException;
import com.example.product_service.model.Category;
import com.example.product_service.model.Product;
import com.example.product_service.model.ProductStatus;
import com.example.product_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    @Transactional
    public Product updateProduct(Product product, Long id){
        Product updateProduct=getProductById(id);
        checkIsValid(product);

        updateProduct.setName(product.getName());
        updateProduct.setCategory(product.getCategory());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setStatus(product.getStatus());
        updateProduct.setSKU(product.getSKU());
        updateProduct.setOwnerId(product.getOwnerId());

        return productRepository.save(updateProduct);
    }

    @Transactional
    public void deleteProduct(Long id){
        getProductById(id);
        productRepository.deleteById(id);
    }

    @Transactional
    public Product createProduct(Product product){
        checkIsValid(product);
        return productRepository.save(product);
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(
                ()-> new ObjectNotFoundException("Product with id "+ id +" was not found"));
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> getProductByName(String name){
        return productRepository.findByNameContaining(name);
    }

    public List<Product> getProductByCategory(Category category){
        return productRepository.findByCategory(category);
    }

    public List<Product> getProductByOwner(Long user_id){
        return productRepository.findByOwnerId(user_id);
    }

    public List<Product> getProductByStatus(ProductStatus status){
        return productRepository.findByStatus(status);
    }

    public List<Product> getProductBySKU(String sku){
        return productRepository.findBySKUContaining(sku);
    }

    private void checkIsValid(Product product){
        if(product.getName().length()<=3){
            throw new IllegalArgumentException("Product name cannot be smaller than 3 characters");
        }
        if(product.getSKU().length()!=10){
            throw new IllegalArgumentException("Product SKU should have 10 characters");
        }
        if(product.getPrice() <=0.0){
            throw new IllegalArgumentException("Product price cannot less or equals to zero");
        }
    }
}