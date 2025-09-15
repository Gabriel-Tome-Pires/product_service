package com.example.product_service;

import org.springframework.boot.SpringApplication;

public class TestDemoApplication {

    public static void main(String[] args) {
        SpringApplication.from(ProductServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
