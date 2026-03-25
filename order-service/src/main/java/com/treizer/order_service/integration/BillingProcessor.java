package com.treizer.order_service.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.treizer.order_service.entity.OrderEntity;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class BillingProcessor {
    
    private final BillingRetryProcessor retryProcessor;
    private static final Logger log = LoggerFactory.getLogger(BillingProcessor.class);

    public BillingProcessor(BillingRetryProcessor retryProcessor) {
        this.retryProcessor = retryProcessor;
    }

    @CircuitBreaker(name = "billingService", fallbackMethod = "billingFallback")
    public void processBilling(OrderEntity order) {
        retryProcessor.processBillingWithRetry(order);
    }

    public void billingFallback(OrderEntity order, Throwable ex) {
        log.error("Fallback activado para orden {}", order.getId(), ex);
        
        order.markAsBillingFailed(); // pesist for dirty checking
    }
    
}
