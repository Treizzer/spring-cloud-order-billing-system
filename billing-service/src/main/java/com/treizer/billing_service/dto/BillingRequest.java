package com.treizer.billing_service.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class BillingRequest {

    private final Long orderId;
    private final BigDecimal amount;

    @JsonCreator
    public BillingRequest(
        @JsonProperty("orderId") Long orderId,
        @JsonProperty("amount") BigDecimal amount
    ) {
        if (orderId == null || orderId < 1) {
            throw new IllegalArgumentException("El ID no es válido "+ orderId);
        }

        if (amount == null || amount.compareTo(BigDecimal.ONE) <= 0) {
            throw new IllegalArgumentException("Se necesita el mónto de la factura");
        }

        this.orderId = orderId;
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    
}
