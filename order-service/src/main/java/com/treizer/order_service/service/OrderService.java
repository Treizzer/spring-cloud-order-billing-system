package com.treizer.order_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.treizer.order_service.dto.OrderRequest;
import com.treizer.order_service.dto.OrderResponse;
import com.treizer.order_service.entity.OrderEntity;
import com.treizer.order_service.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public OrderResponse createOrder(OrderRequest request) {
        OrderEntity entity = new OrderEntity(
            request.getCustomerName(),
            request.getAmount()
        );

        OrderEntity saved = repository.save(entity);

        return new OrderResponse(
            saved.getId(),
            saved.getCustomerName(),
            saved.getAmount(),
            saved.getStatus().name(),
            saved.getCreatedAt()
        );
    }

    public List<OrderResponse> getAllOrders() {
        return repository.findAll()
            .stream()
            .map(order -> new OrderResponse(
                order.getId(),
                order.getCustomerName(),
                order.getAmount(),
                order.getStatus().name(),
                order.getCreatedAt()
            ))
            .collect(Collectors.toList());
    }
    
}
