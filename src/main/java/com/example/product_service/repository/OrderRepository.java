package com.example.product_service.repository;


import com.example.product_service.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.product_service.model.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
        Order findByUserId(Long userId);
        Order findByOrderStatus(OrderStatus orderStatus);
        List<Order> findByDateBefore(LocalDateTime date);
        List<Order> findByDateAfter(LocalDateTime date);
}
