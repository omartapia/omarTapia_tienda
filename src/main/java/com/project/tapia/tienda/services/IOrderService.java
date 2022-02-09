package com.project.tapia.tienda.services;

import com.project.tapia.tienda.models.Order;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface IOrderService {
    List<Order> findAll();

    Order persist(Order order);

    Order findById(Long id);

    List<Order> processStock(List<Order> orders);

    Mono<ByteArrayInputStream> rerportA();

    Mono<ByteArrayInputStream> rerportB();
}
