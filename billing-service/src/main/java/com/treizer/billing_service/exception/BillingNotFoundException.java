package com.treizer.billing_service.exception;

public class BillingNotFoundException extends RuntimeException {

    public BillingNotFoundException(Long id) {
        super("Facturación no encontrada. ID: "+ id);
    }
    
}
