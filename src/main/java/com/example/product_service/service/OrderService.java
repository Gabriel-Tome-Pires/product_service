package com.example.product_service.service;
import com.example.product_service.exception.ObjectNotFoundException;
import com.example.product_service.model.Order;
import com.example.product_service.model.OrderStatus;
import com.example.product_service.repository.OrderRepository;
import com.example.product_service.repository.OrderStatusRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            Order order=getById(id);
            if(order.getOrderStatus().getStatusName().equals("PAID") ||
                    order.getOrderStatus().getStatusName().equals("DELIVERED") ||
                    order.getOrderStatus().getStatusName().equals("CANCELLED") ){
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "You cannot delete a order with the status: " + order.getOrderStatus().getStatusName());
            }
            orderRepository.deleteById(id);
        }

        @Transactional
        public Order update(Order order, Long id) {
            Order updateOrder=getById(id);

            if((updateOrder.getOrderStatus().getStatusName().equals("PAID") &&  !order.getOrderStatus().getStatusName().equals("DELIVERED"))||
                    updateOrder.getOrderStatus().getStatusName().equals("DELIVERED") ||
                    updateOrder.getOrderStatus().getStatusName().equals("CANCELLED") ){
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "You cannot update this order. Order Status: " + updateOrder.getOrderStatus().getStatusName());
            }

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
