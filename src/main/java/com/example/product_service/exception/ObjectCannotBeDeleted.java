package com.example.product_service.exception;

public class ObjectCannotBeDeleted extends RuntimeException {
    public ObjectCannotBeDeleted(String message) {
        super(message);
    }
}
