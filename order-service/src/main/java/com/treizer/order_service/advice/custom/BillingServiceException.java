package com.treizer.order_service.advice.custom;

public class BillingServiceException extends RuntimeException {

    public BillingServiceException(String message) {
        super(message);
    }
    
}
