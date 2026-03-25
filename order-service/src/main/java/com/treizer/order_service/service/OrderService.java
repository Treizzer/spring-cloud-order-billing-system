package com.treizer.order_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.treizer.order_service.dto.OrderRequest;
import com.treizer.order_service.dto.OrderResponse;
import com.treizer.order_service.entity.OrderEntity;
import com.treizer.order_service.entity.OrderStatus;
import com.treizer.order_service.integration.BillingProcessor;
import com.treizer.order_service.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final BillingProcessor billingProcessor;
    
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository repository, BillingProcessor billingProcessor) {
        this.repository = repository;
        this.billingProcessor = billingProcessor;
    }

    // future: add "Orchestrator"
    // @Transactional(noRollbackFor = BillingServiceException.class)
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        OrderEntity entity = new OrderEntity(
            request.getCustomerName(),
            request.getAmount()
        );

        log.info("Creando orden para cliente {}", request.getCustomerName());
        OrderEntity saved = repository.save(entity);
        log.info("Orden creada con id {}", saved.getId());

        saved.markAsPaid();

        billingProcessor.processBilling(saved);

        if (saved.getStatus() == OrderStatus.FACTURACION_FALLO) {
            log.warn("Orden {} con fallo en billing", saved.getId());
        }
        else {
            saved.markAsBilled();
        }

        // try {
        //     // future: possible payment action
        //     saved.markAsPaid();
        //     billingProcessor.processBilling(saved);
        //     saved.markAsBilled();
        // } catch (Exception e) {
        //     log.error("El servicio Billing falló en la orden {}", saved.getId(), e);
        //     saved.markAsBillingFailed();
        //     throw new BillingServiceException("Servicio Billing no disponible");
        // }

        return new OrderResponse(
            saved.getId(),
            saved.getCustomerName(),
            saved.getAmount(),
            saved.getStatus().name(),
            saved.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return repository.findAll()
            .stream()
            .map(order -> new OrderResponse(
                order.getId(),
                order.getCustomerName(),
                order.getAmount(),
                order.getStatus().name(),
                order.getCreatedAt()
            ))
            .collect(Collectors.toList());
    }
    
}
