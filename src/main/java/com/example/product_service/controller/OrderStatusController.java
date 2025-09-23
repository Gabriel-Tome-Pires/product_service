package com.example.product_service.controller;

import com.example.product_service.model.OrderStatus;
import com.example.product_service.service.OrderStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("orderStatus")
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    @PostMapping("/add")
    public ResponseEntity<OrderStatus> addOrderStatus(@RequestBody OrderStatus orderStatus) {
        OrderStatus newOrderStatus=orderStatusService.createOrderStatus(orderStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrderStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderStatus> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatus orderStatus){
        OrderStatus updatedStatus=orderStatusService.updateOrderStatus(id,orderStatus);
        return ResponseEntity.status(HttpStatus.OK).body(updatedStatus);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<OrderStatus> deleteOrderStatus(@PathVariable Long id){
        orderStatusService.deleteOrderStatus(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable Long id){
        OrderStatus orderStatus=orderStatusService.getOrderStatusById(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderStatus);
    }

    @GetMapping
    public ResponseEntity<List<OrderStatus>> getAllOrderStatus(){
        List<OrderStatus> orderStatuses=orderStatusService.getAllOrderStatuses();
        return ResponseEntity.status(HttpStatus.OK).body(orderStatuses);
    }

}
