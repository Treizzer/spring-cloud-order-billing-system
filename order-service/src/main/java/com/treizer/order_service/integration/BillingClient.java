package com.treizer.order_service.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.treizer.order_service.dto.BillingRequest;

@FeignClient(
    name = "billing-service"
    // configuration = FeignSecurityConfig.class
)
public interface BillingClient {

    @PostMapping("/v1/billings")
    void createBilling(@RequestBody BillingRequest request);
    
}
