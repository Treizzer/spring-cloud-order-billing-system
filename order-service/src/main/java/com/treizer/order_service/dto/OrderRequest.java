package com.treizer.order_service.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class OrderRequest {

    private final String customerName;
    private final BigDecimal amount;

    // Telling "Jackson" how to build (deserialization)
    @JsonCreator
    public OrderRequest(
        @JsonProperty("customerName") String customerName, 
        @JsonProperty("amount") BigDecimal amount
    ) {
        if (customerName.isBlank()) {
            throw new IllegalArgumentException("Se necesita el nombre del cliente");
        }

        if (amount == null || amount.compareTo(BigDecimal.ONE) <= 0) {
            throw new IllegalArgumentException("Se necesita el precio de la orden");
        }

        this.customerName = customerName;
        this.amount = amount;
    }

    public String getCustomerName() { return customerName; }

    public BigDecimal getAmount() { return amount; }
    
}
