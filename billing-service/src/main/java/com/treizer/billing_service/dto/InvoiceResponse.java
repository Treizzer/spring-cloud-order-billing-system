package com.treizer.billing_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvoiceResponse {

    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private BigDecimal tax;
    private BigDecimal total;
    private LocalDateTime createdAt;

    public InvoiceResponse(
        Long id, Long orderId, 
        BigDecimal amount, BigDecimal tax, BigDecimal total, 
        LocalDateTime createdAt
    ) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.tax = tax;
        this.total = total;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    
    
}
