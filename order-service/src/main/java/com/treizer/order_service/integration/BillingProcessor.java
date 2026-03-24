package com.treizer.order_service.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.treizer.order_service.dto.BillingRequest;
import com.treizer.order_service.entity.OrderEntity;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class BillingProcessor {
    
    private final BillingClient billingClient;

    private static final Logger log = LoggerFactory.getLogger(BillingProcessor.class);

    public BillingProcessor(BillingClient billingClient) {
        this.billingClient = billingClient;
    }

    @Retry(name = "billingService")
    @CircuitBreaker(
        name = "billingService", 
        fallbackMethod = "billingFallback"
    )
    public void processBilling(OrderEntity order) {
        BillingRequest billingRequest = new BillingRequest(order.getId(), order.getAmount());
        log.info("Intentando crear billing para orden {}", order.getId());
        billingClient.createBilling(billingRequest);
    }
    
}
