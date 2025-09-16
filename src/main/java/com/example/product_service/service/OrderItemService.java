package com.example.product_service.service;

import com.example.product_service.exception.ObjectNotFoundException;
import com.example.product_service.model.Order;
import com.example.product_service.model.OrderItem;
import com.example.product_service.model.Product;
import com.example.product_service.repository.OrderItemRepository;
import com.example.product_service.repository.OrderRepository;
import com.example.product_service.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("OrderItem with id " + id + " not found"));
    }

    public List<OrderItem> getOrderItemsByProduct(Product product) {
        return orderItemRepository.findByProduct(product);
    }

    public List<OrderItem> getOrderItemByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    public OrderItem saveOrderItem(OrderItem orderItem) {
        checkIfIsValid(orderItem);

        return orderItemRepository.save(orderItem);
    }

    public void deleteOrderItemById(Long id){
        getOrderItemById(id);
        orderItemRepository.deleteById(id);
    }

    public OrderItem updateOrderItem(Long id, OrderItem orderItem){
        checkIfIsValid(orderItem);

        OrderItem updateOrderItem = getOrderItemById(id);
        updateOrderItem.setOrder(orderItem.getOrder());
        updateOrderItem.setProduct(orderItem.getProduct());
        return orderItemRepository.save(updateOrderItem);
    }

    private void checkIfIsValid(OrderItem orderItem){
        orderRepository.findById(orderItem.getOrder().getId());
        productService.getProductById(orderItem.getProduct().getId());

    }
}
