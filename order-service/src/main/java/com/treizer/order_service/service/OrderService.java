package com.treizer.order_service.service;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.treizer.order_service.entity.OrderEntity;
import com.treizer.order_service.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public OrderEntity createOrder(@NonNull OrderEntity entity) {
        return repository.save(entity);
    }

    public List<OrderEntity> getAllOrders() {
        return repository.findAll();
    }
    
}
