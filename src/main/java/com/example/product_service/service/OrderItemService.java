package com.example.product_service.service;

import com.example.product_service.exception.ObjectNotFoundException;
import com.example.product_service.model.Order;
import com.example.product_service.model.OrderItem;
import com.example.product_service.model.Product;
import com.example.product_service.repository.OrderItemRepository;
import com.example.product_service.repository.OrderRepository;
import com.example.product_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderItem saveOrderItem(OrderItem orderItem) {
        checkIfIsValid(orderItem);

        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public void deleteOrderItemById(Long id){
        getOrderItemById(id);
        orderItemRepository.deleteById(id);
    }

    @Transactional
    public OrderItem updateOrderItem(Long id, OrderItem orderItem){
        checkIfIsValid(orderItem);
        OrderItem updateOrderItem = getOrderItemById(id);
        //TODO do not allow to update, if OrderStatus is canceled, paid or delivered

        updateOrderItem.setOrder(orderItem.getOrder());
        updateOrderItem.setProduct(orderItem.getProduct());

        return orderItemRepository.save(updateOrderItem);
    }


    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("OrderItem with id " + id + " was not found"));
    }

    public List<OrderItem> getOrderItemsByProduct(Product product) {
        return orderItemRepository.findByProduct(product);
    }

    public List<OrderItem> getOrderItemByOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Order with id "+ id +" was not found"));
        return orderItemRepository.findByOrder(order);
    }

    private void checkIfIsValid(OrderItem orderItem){
        Long orderId=orderItem.getOrder().getId();
        Long productId=orderItem.getProduct().getId();

        orderRepository.findById(orderId).orElseThrow(
                ()-> new ObjectNotFoundException("Order with id "+orderId+" was not found")
        );
        productRepository.findById(productId).orElseThrow(
                ()-> new ObjectNotFoundException("Product with id "+productId+" was not found")
        );
    }
}
