package com.treizer.billing_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treizer.billing_service.dto.BillingRequest;
import com.treizer.billing_service.dto.BillingResponse;
import com.treizer.billing_service.service.BillingService;

@RestController
@RequestMapping("/v1/billings")
public class BillingController {

    private final BillingService service;

    public BillingController(BillingService service) {
        this.service = service;
    }

    @PostMapping
    public BillingResponse create(@RequestBody BillingRequest request) {
        return service.createBilling(request);
    }
    
}
