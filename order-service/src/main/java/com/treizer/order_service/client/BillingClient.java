package com.treizer.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.treizer.order_service.dto.BillingRequest;

@FeignClient(name = "billing-service")
public interface BillingClient {

    @PostMapping("/v1/billings")
    void createBilling(@RequestBody BillingRequest request);
    
}
