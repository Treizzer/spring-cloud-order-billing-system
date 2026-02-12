package com.treizer.order_service.controller;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treizer.order_service.entity.OrderEntity;
import com.treizer.order_service.service.OrderService;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public OrderEntity create(@NonNull @RequestBody OrderEntity entity) {
        return service.createOrder(entity);
    }

    @GetMapping
    public List<OrderEntity> getAll() {
        return service.getAllOrders();
    }
    
}
