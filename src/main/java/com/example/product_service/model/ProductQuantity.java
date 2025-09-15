package com.example.product_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;
    @NotNull
    private int quantity;
    @OneToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    public ProductQuantity(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }
}
