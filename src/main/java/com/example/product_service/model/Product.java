package com.example.product_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;
    @NotBlank
    @NotNull
    private String name;
    private String description;
    @NotNull
    private double price;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name= "status_id")
    private ProductStatus status;
    @NotNull
    //@Column(name = "product_owner_id")
    private Long ownerId;
    @NotBlank
    @NotNull
    private String SKU;

    public Product(String name, String description, double price, Category category, ProductStatus status, long owner_id, String SKU) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.status = status;
        this.ownerId = owner_id;
        this.SKU = SKU;
    }
}
