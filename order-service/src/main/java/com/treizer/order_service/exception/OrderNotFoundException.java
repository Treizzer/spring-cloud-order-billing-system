package com.treizer.order_service.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super("Orden no encontrada. ID: "+ id);
    }
    
}
