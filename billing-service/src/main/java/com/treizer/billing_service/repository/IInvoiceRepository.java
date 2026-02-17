package com.treizer.billing_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treizer.billing_service.entity.InvoiceEntity;

public interface IInvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    
}
