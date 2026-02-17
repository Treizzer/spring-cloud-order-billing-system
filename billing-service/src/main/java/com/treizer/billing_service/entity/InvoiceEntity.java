package com.treizer.billing_service.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false, updatable = false)
    private Long orderId;

    @Column(precision = 10, scale = 2, nullable = false, updatable = false)
    private BigDecimal amount;

    @Column(precision = 10, scale = 2, nullable = false, updatable = false)
    private BigDecimal tax;

    @Column(precision = 10, scale = 2, nullable = false, updatable = false)
    private BigDecimal total;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected InvoiceEntity() {
    }

    public InvoiceEntity(Long orderId, BigDecimal amount, BigDecimal tax, BigDecimal total) {
        this.orderId = Objects.requireNonNull(orderId, "orderId no puede ser null");
        this.amount = Objects.requireNonNull(amount, "amount no puede ser null");
        this.tax = Objects.requireNonNull(tax, "tax no puede ser null");
        this.total = Objects.requireNonNull(total, "total no puede ser null");
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
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
