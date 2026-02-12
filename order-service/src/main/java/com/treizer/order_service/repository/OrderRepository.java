package com.treizer.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treizer.order_service.entity.OrderEntity;

// Spring Data JPA automatically detects all interfaces are extended by:
//  JpaRepository, CrudRepository, etc.
// @Repository is not necessary
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    
}
