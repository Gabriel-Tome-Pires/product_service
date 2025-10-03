package com.example.product_service.config;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "app.kafka.topics")
@Validated
@Getter
@Setter
public class KafkaTopicProperties {

    private TopicProperties orderCreated;
    private TopicProperties orderPaid;

    @Getter
    @Setter
    public static class TopicProperties {
        @Min(1)
        private int partitions = 1;
        @Min(1)
        private short replication = 1;
    }
}
