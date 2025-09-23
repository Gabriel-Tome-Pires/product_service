package com.example.product_service.controller;

import com.example.product_service.model.Category;
import com.example.product_service.model.Product;
import com.example.product_service.model.ProductStatus;
import com.example.product_service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product newProduct=productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updateProduct=productService.updateProduct(product, id);
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product=productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products=productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable String name) {
        List<Product> products=productService.getProductByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<List<Product>> getProductBySku(@PathVariable String sku) {
        List<Product> products=productService.getProductBySKU(sku);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<List<Product>> getProductByownerID(@PathVariable Long id) {
        List<Product> products=productService.getProductByOwner(id);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/status")
    public ResponseEntity<List<Product>> getProductByStatus(@RequestBody ProductStatus productStatus) {
        List<Product> products=productService.getProductByStatus(productStatus);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Product>> getProductByCategory(@RequestBody Category category) {
        List<Product> products=productService.getProductByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }


}
