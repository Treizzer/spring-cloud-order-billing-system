package com.treizer.order_service.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING) // Save it like String
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // It's executed just before to insert on database
    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = OrderStatus.CREADO;
        }
        createdAt = LocalDateTime.now();
    }

    public OrderEntity() {
    }

    public OrderEntity(String customerName, BigDecimal amount) {
        setCustomerName(customerName);
        setAmount(amount);
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Nombre del cliente obligatorio");
        }

        this.customerName = customerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio es obligatorio");
        }

        this.amount = amount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void markAsPaid() {
        if (status != OrderStatus.CREADO) {
            throw new IllegalStateException("Solo se pueden pagar órdenes creadas, actual: "+ status);
        }

        status = OrderStatus.PAGADO;
    }

    public void markAsBilled() {
        if (status != OrderStatus.PAGADO && status != OrderStatus.FACTURACION_FALLO) {
            throw new IllegalStateException(
                "La orden no puede facturarse desde el estado: "+ status
            );
        }

        status = OrderStatus.FACTURADO;
    }

    public void markAsBillingFailed() {
        if (status != OrderStatus.PAGADO) {
            throw new IllegalStateException("La orden no ha sido pagada: "+ status.toString());
        }

        status = OrderStatus.FACTURACION_FALLO;
    }

    public void markAsCanceled() {
        if (status == OrderStatus.FACTURACION_FALLO) {
            throw new IllegalStateException("No se puede cancelar una orden en proceso de facturación");
        }
        if (status == OrderStatus.FACTURADO) {
            throw new IllegalStateException("No se puede cancelar una orden facturada");
        }
        if (status == OrderStatus.CANCELADO) {
            throw new IllegalStateException("La orden ya está cancelada");
        }

        status = OrderStatus.CANCELADO;
    }

}
