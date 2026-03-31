package com.treizer.order_service.integration;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.treizer.order_service.dto.BillingRequest;
import com.treizer.order_service.entity.OrderEntity;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;

@Service
public class BillingProcessorManual {

    private final BillingClient client;

    private final Retry retry;
    private final CircuitBreaker circuitBreaker;
    private static final Logger log = LoggerFactory.getLogger(BillingProcessorManual.class);

    public BillingProcessorManual(BillingClient client) {
        this.client = client;
        
        retry = Retry.ofDefaults("billingService");
        circuitBreaker = CircuitBreaker.ofDefaults("billingService");
        configEventPublishers();
    }

    private void configEventPublishers() {
        retry.getEventPublisher()
            .onRetry(event -> log.warn(
                "Retry intento {} para {}", 
                event.getNumberOfRetryAttempts(), 
                event.getName()
            ))
            .onError(event -> log.error(
                "Retry falló: {}", event.getLastThrowable().getMessage()
            ));

        circuitBreaker.getEventPublisher()
            .onStateTransition(event -> log.warn(
                "CB estado cambiado: {}",
                event.getStateTransition()
            ))
            .onCallNotPermitted(event -> log.warn(
                "Llamada bloqueada por Circuit Breaker"
            ))
            .onError(event -> log.error(
                "Llamada fallida: {}", event.getThrowable().getMessage()
            ));
    }
    
    public void processBilling(OrderEntity order) {
        Supplier<Void> billingCall = () -> {
            client.createBilling(new BillingRequest(
                order.getId(), 
                order.getAmount()
            ));

            return null;
        };

        Supplier<Void> decorated = CircuitBreaker.decorateSupplier(
            circuitBreaker,
            Retry.decorateSupplier(
                retry,
                billingCall
            )
        );

        try {
            decorated.get();
            order.markAsBilled();

        } catch (Exception ex) {
            log.error("\nFallback activado para ordern {}", order.getId(), ex);
            order.markAsBillingFailed();
        }
    }

}
