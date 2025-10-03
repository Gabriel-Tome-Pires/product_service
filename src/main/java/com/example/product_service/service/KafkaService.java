package com.example.product_service.service;

import com.example.product_service.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String, Order> kafkaTemplateOrder;
    //@KafkaListener(topicPartitions = @TopicPartition(topic = "napoleao-order-processed", partitions = { "0" }), containerFactory = "orderKafkaListenerContainerFactory")
    @SuppressWarnings("null")
    public void sendMessageOrderPaid(Order order) {
        String key=order.getId().toString();
        System.out.println("Sending Order: " + order.getId());
        kafkaTemplateOrder.send("order-paid", key, order);
    }

    @SuppressWarnings("null")
    public void sendMessageOrderCreated(Order order) {
        String key=order.getId().toString();
        System.out.println("Sending Order: " + order.getId());
        kafkaTemplateOrder.send("order-created", key, order);
    }
}
