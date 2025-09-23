package com.example.product_service.service;
import com.example.product_service.exception.ObjectNotFoundException;
import com.example.product_service.model.Order;
import com.example.product_service.model.OrderStatus;
import com.example.product_service.repository.OrderRepository;
import com.example.product_service.repository.OrderStatusRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

    @Service
    @AllArgsConstructor
    public class OrderService {
        private final OrderRepository orderRepository;
        private final OrderStatusRepository orderStatusRepository;

        @Transactional
        public Order save(Order order){
            if(order.getCreatedAt() == null){
                order.setCreatedAt(LocalDateTime.now());
            }
            //TODO check initial status

            return orderRepository.save(order);
        }

        @Transactional
        public void delete(Long id){
            getById(id);
            //TODO check Status before delete
            orderRepository.deleteById(id);
        }

        @Transactional
        public Order update(Order order, Long id){
            Order updateOrder=getById(id);

            updateOrder.setOrderStatus(order.getOrderStatus());
            updateOrder.setUserId(order.getUserId());

            return orderRepository.save(updateOrder);
        }

        public Order getById(Long id){
            return orderRepository.findById(id).orElseThrow(
                    ()-> new ObjectNotFoundException("Order with id "+ id +" was not found"));
        }

        public List<Order> getAll(){
            return orderRepository.findAll();
        }

        public List<Order> getByUserId(Long id){
            return orderRepository.findByUserId(id);
        }

        public List<Order> getByDate(LocalDateTime date){
            return orderRepository.findByCreatedAt(date);
        }

        public List<Order> getByBeforeDate(LocalDateTime date){
            return orderRepository.findByCreatedAtBefore(date);
        }

        public List<Order> getByAfterDate(LocalDateTime date){
            return orderRepository.findByCreatedAtAfter(date);
        }

        public List<Order> getByStatus(Long orderStatusId){
            OrderStatus orderStatus=orderStatusRepository.findById(orderStatusId).orElseThrow(
                    ()-> new ObjectNotFoundException("Order Status with id "+orderStatusId+" was not found!"));
            return orderRepository.findByOrderStatus(orderStatus);
        }

    }
