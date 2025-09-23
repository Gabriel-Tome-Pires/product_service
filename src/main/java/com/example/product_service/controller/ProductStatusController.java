package com.example.product_service.controller;

import com.example.product_service.model.ProductStatus;
import com.example.product_service.service.ProductStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/productStatus")
public class ProductStatusController {
    private final ProductStatusService productStatusService;

    @PostMapping("/add")
    public ResponseEntity<ProductStatus> createProductStatus(@RequestBody ProductStatus productStatus) {
        ProductStatus newProductStatus=productStatusService.createProductStatus(productStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProductStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductStatus> updateProductStatus(@PathVariable Long id, @RequestBody ProductStatus productStatus) {
        ProductStatus updatedStatus=productStatusService.updateProductStatus(productStatus,id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedStatus);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductStatus> deleteProductStatus(@PathVariable Long id) {
        productStatusService.deleteProductStatus(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductStatus> getProductStatus(@PathVariable Long id) {
        ProductStatus productStatus=productStatusService.getProductStatusById(id);
        return ResponseEntity.status(HttpStatus.OK).body(productStatus);
    }

    @GetMapping
    public ResponseEntity<List<ProductStatus>> getAllProductStatus() {
        List<ProductStatus> productStatuses=productStatusService.getAllProductStatuses();
        return ResponseEntity.status(HttpStatus.OK).body(productStatuses);
    }
}
