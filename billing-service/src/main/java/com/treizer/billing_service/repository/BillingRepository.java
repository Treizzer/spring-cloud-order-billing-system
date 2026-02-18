package com.treizer.billing_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treizer.billing_service.entity.BillingEntity;

public interface BillingRepository extends JpaRepository<BillingEntity, Long> {
    
}
