package com.example.product_service.service;
import com.example.product_service.exception.ObjectNotFoundException;
import com.example.product_service.model.Order;
import com.example.product_service.model.OrderStatus;
import com.example.product_service.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

    @Service
    @AllArgsConstructor
    public class OrderService {
        private final OrderRepository orderRepository;

        public Order save(Order order){
            if(!order.getDate().isEqual(LocalDateTime.now())){
               order.setDate(LocalDateTime.now());
            }
            //TODO check initial status

            return orderRepository.save(order);
        }

        public void delete(Long id){
            getById(id);
            //TODO check Status before delete
            orderRepository.deleteById(id);
        }

        public Order update(Order order){
            Order updateOrder=getById(order.getId());

            updateOrder.setOrderStatus(order.getOrderStatus());
            updateOrder.setDate(order.getDate());
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
            return orderRepository.findByDate(date);
        }

        public List<Order> getByBeforeDate(LocalDateTime date){
            return orderRepository.findByDateBefore(date);
        }

        public List<Order> getByAfterDate(LocalDateTime date){
            return orderRepository.findByDateAfter(date);
        }

        public List<Order> getByStatus(OrderStatus orderStatus){
            return orderRepository.findByOrderStatus(orderStatus);
        }

    }
