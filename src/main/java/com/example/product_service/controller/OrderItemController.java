package com.example.product_service.controller;

import com.example.product_service.model.Order;
import com.example.product_service.model.OrderItem;
import com.example.product_service.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/OrderItem")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PostMapping("/add")
    public ResponseEntity<OrderItem> addOrderItem(@RequestBody OrderItem orderItem){
        OrderItem newOrderItem=orderItemService.saveOrderItem(orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrderItem);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<OrderItem> deleteOrderItem(@PathVariable Long id){
        orderItemService.deleteOrderItemById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id){
        OrderItem orderItem=orderItemService.getOrderItemById(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderItem);
    }

    @GetMapping("/byOrder/{order_id}")
    public ResponseEntity<List<OrderItem>> getOrderItemByOrderId(@PathVariable Long order_id){
        List<OrderItem> orderItems=orderItemService.getOrderItemByOrder(order_id);
        return ResponseEntity.status(HttpStatus.OK).body(orderItems);
    }


}

