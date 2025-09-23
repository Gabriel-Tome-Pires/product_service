package com.example.product_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;
    @NotNull
    //@Column(name = "user_id")
    private long userId;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private OrderStatus orderStatus;
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderItem> items;

    public Order(long userId, OrderStatus orderStatus, LocalDateTime createdAt) {
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }
}
