package com.treizer.order_service.advice.custom;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super("Orden no encontrada. ID: "+ id);
    }
    
}
