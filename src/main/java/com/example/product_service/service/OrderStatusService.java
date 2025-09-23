package com.example.product_service.service;

import com.example.product_service.exception.ObjectCannotBeDeleted;
import com.example.product_service.exception.ObjectNotFoundException;
import com.example.product_service.model.OrderStatus;
import com.example.product_service.repository.OrderRepository;
import com.example.product_service.repository.OrderStatusRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.errors.DuplicateResourceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderStatus createOrderStatus(OrderStatus orderStatus) {
        String validStatus=validateOrderStatus(orderStatus.getStatusName());
        orderStatus.setStatusName(validStatus);

        return orderStatusRepository.save(orderStatus);
    }

    @Transactional
    public OrderStatus updateOrderStatus(Long id, OrderStatus orderStatus) {
        String validateStatus=validateOrderStatus(orderStatus.getStatusName());
        OrderStatus updateOrderStatus = getOrderStatusById(id);

        updateOrderStatus.setStatusName(validateStatus);

        return orderStatusRepository.save(updateOrderStatus);
    }

    @Transactional
    public void deleteOrderStatus(Long id){
        OrderStatus orderStatus= getOrderStatusById(id);
        if(orderRepository.existsByOrderStatus(orderStatus)){
            throw new ObjectCannotBeDeleted
                    ("Order status with id "+id+" and name "+orderStatus.getStatusName()+" is in use by at least one order and cannot be deleted");
        }
        orderStatusRepository.deleteById(id);
    }

    public List<OrderStatus> getAllOrderStatuses(){
        return orderStatusRepository.findAll();
    }

    public OrderStatus getOrderStatusById(Long id){
        return orderStatusRepository.findById(id).orElseThrow(
                ()-> new ObjectNotFoundException("Order Status with id "+id+" was not found!"));
    }

    private String validateOrderStatus(String status){
        String validatedStatus=status.toLowerCase().trim();

        List<OrderStatus> orderStatuses = getAllOrderStatuses();
        for(OrderStatus orderStatus : orderStatuses){
            if(orderStatus.getStatusName().equals(validatedStatus)){
                throw new DuplicateResourceException("Order status with the name "+validatedStatus+" already exists");
            }
        }

        return validatedStatus;
    }
}
