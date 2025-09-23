package com.example.product_service.controller;

import com.example.product_service.model.Order;
import com.example.product_service.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order newOrder= orderService.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order updatedOrder=orderService.update(order, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order=orderService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders=orderService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getAllOrdersByUserId(@RequestParam Long userId) {
        List<Order> orders=orderService.getByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<List<Order>> getAllOrdersByStatusId(@RequestParam Long statusId) {
        List<Order> orders=orderService.getByStatus(statusId);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/byDate")
    public ResponseEntity<List<Order>> getOrdersByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<Order> orders=orderService.getByDate(date);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/beforeDate")
    public ResponseEntity<List<Order>> getOrdersBeforeDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<Order> orders=orderService.getByBeforeDate(date);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @GetMapping("/afterDate")
    public ResponseEntity<List<Order>> getOrdersAfterDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<Order> orders=orderService.getByAfterDate(date);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

}
