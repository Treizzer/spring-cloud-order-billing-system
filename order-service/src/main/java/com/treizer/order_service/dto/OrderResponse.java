package com.treizer.order_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {

    private Long id;
    private String customerName;
    private BigDecimal amount;
    private String status;
    private LocalDateTime createdAt;

    public OrderResponse(Long id, String customerName, BigDecimal amount, String status, LocalDateTime createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
}
