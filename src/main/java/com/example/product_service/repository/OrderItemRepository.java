package com.example.product_service.repository;

import com.example.product_service.model.Order;
import com.example.product_service.model.OrderItem;
import com.example.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
    List<OrderItem> findByProduct(Product product);
}
