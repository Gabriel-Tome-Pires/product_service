package com.example.product_service.controller;

import com.example.product_service.model.Product;
import com.example.product_service.model.ProductQuantity;
import com.example.product_service.service.ProductQuantityService;
import com.example.product_service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/ProductQuantity")
public class ProductQuantityController {
    private final ProductQuantityService productQuantityService;
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductQuantity> addProductQuantity(@RequestBody ProductQuantity productQuantity) {
        ProductQuantity newproductQuantity=productQuantityService.createProductQuantity(productQuantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(newproductQuantity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductQuantity> updateProductQuantity(@PathVariable long id, @RequestBody ProductQuantity productQuantity) {
        ProductQuantity updatedProductQuantity=productQuantityService.updateProductQuantity(productQuantity, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProductQuantity);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductQuantity> deleteProductQuantity(@PathVariable long id) {
        productQuantityService.getProductQuantityById(id);
        productQuantityService.deleteProductQuantityById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductQuantity> getProductQuantityById(@PathVariable long id) {
        ProductQuantity productQuantity=productQuantityService.getProductQuantityById(id);
        return ResponseEntity.status(HttpStatus.OK).body(productQuantity);
    }

    @GetMapping("/byProduct/{id}")
    public ResponseEntity<ProductQuantity> getProductQuantityByProductId(@PathVariable long id) {
        Product product=productService.getProductById(id);
        ProductQuantity productQuantity=productQuantityService.getProductQuantityByProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(productQuantity);
    }
}
