package com.treizer.order_service.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.treizer.order_service.dto.BillingRequest;
import com.treizer.order_service.entity.OrderEntity;

import io.github.resilience4j.retry.annotation.Retry;

@Service
public class BillingRetryProcessor {

    private final BillingClient client;
    private static final Logger log = LoggerFactory.getLogger(BillingRetryProcessor.class);

    public BillingRetryProcessor(BillingClient client) {
        this.client = client;
    }

    @Retry(name = "billingService")
    public void processBillingWithRetry(OrderEntity order) {
        // BillingRequest billingRequest = new BillingRequest(order.getId(), order.getAmount());
        log.info("Intentando crear billing para orden {}", order.getId());

        client.createBilling(new BillingRequest(
            order.getId(),
            order.getAmount()
        ));
    }
    
}
