package com.example.product_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
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
    private LocalDateTime date;

    public Order(long userId, OrderStatus orderStatus, LocalDateTime date) {
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.date = date;
    }
}
