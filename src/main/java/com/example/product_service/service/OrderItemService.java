package com.example.product_service.service;

import com.example.product_service.exception.ObjectNotFoundException;
import com.example.product_service.model.OrderItem;
import com.example.product_service.repository.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("OrderItem with id " + id + " not found"));
    }

    public OrderItem saveOrderItem(OrderItem orderItem) {
        //TODO check if is valid
        return orderItemRepository.save(orderItem);
    }

    public void deleteOrderItemById(Long id){
        getOrderItemById(id);
        orderItemRepository.deleteById(id);
    }

    public OrderItem updateOrderItem(Long id, OrderItem orderItem){
        //TODO check if is valid
        OrderItem updateOrderItem = getOrderItemById(id);
        updateOrderItem.setOrder(orderItem.getOrder());
        updateOrderItem.setProduct(orderItem.getProduct());
        return orderItemRepository.save(updateOrderItem);
    }
}
