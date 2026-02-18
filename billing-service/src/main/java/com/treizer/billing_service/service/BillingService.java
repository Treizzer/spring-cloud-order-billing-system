package com.treizer.billing_service.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.treizer.billing_service.dto.BillingRequest;
import com.treizer.billing_service.dto.BillingResponse;
import com.treizer.billing_service.entity.BillingEntity;
import com.treizer.billing_service.repository.BillingRepository;

@Service
public class BillingService {

    private final BillingRepository repository;

    public BillingService(BillingRepository repository) {
        this.repository = repository;
    }

    public BillingResponse createBilling(BillingRequest request) {
        BigDecimal tax = request.getAmount().multiply(new BigDecimal("0.16"));
        BigDecimal total = request.getAmount().add(tax);

        BillingEntity entity = new BillingEntity(
            request.getOrderId(),
            request.getAmount(),
            tax,
            total
        );

        BillingEntity saved = repository.save(entity);

        return new BillingResponse(
            saved.getId(),
            saved.getOrderId(),
            saved.getAmount(),
            saved.getTax(),
            saved.getTotal(),
            saved.getCreatedAt()
        );
    }
    
}
