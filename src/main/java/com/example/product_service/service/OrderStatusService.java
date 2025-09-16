package com.example.product_service.service;

import com.example.product_service.exception.ObjectNotFoundException;
import com.example.product_service.model.OrderStatus;
import com.example.product_service.repository.OrderStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    public OrderStatus createOrderStatus(OrderStatus orderStatus) {
        validateOrderStatus(orderStatus.getStatus());

        return orderStatusRepository.save(orderStatus);
    }

    public List<OrderStatus> getAllOrderStatuses(){
        return orderStatusRepository.findAll();
    }

    public OrderStatus getOrderStatusById(Long id){
        return orderStatusRepository.findById(id).orElseThrow(
                ()-> new ObjectNotFoundException("Order Status with this id was not found!"));
    }

    public OrderStatus updateOrderStatus(Long id, OrderStatus orderStatus) {
        validateOrderStatus(orderStatus.getStatus());

        OrderStatus updateOrderStatus = getOrderStatusById(id);
        updateOrderStatus.setStatus(orderStatus.getStatus());
        return orderStatusRepository.save(updateOrderStatus);
    }

    public void deleteOrderStatus(Long id){
        getOrderStatusById(id);
        orderStatusRepository.deleteById(id);
    }

    private void validateOrderStatus(String status){
        if(status==null || status.isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
    }
}
