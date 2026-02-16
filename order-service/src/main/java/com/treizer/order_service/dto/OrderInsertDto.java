package com.treizer.order_service.dto;

import java.math.BigDecimal;

public final class OrderInsertDto {

    private final String customerName;
    private final BigDecimal amount;

    public OrderInsertDto(String customerName, BigDecimal amount) {
        if (customerName.isBlank()) {
            throw new IllegalArgumentException("Se necesita el nombre del cliente");
        }
        this.customerName = customerName;

        if (amount == null || amount.compareTo(BigDecimal.ONE) <= 1) {
            throw new IllegalArgumentException("Se necesita el precio de la orden");
        }
        this.amount = amount;
    }

    public String getCustomerName() { return customerName; }

    public BigDecimal getAmount() { return amount; }
    
}
