package com.project.tapia.tienda.services;

import com.project.tapia.tienda.models.Arrangement;
import com.project.tapia.tienda.models.request.ReportTransactionARequest;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface IOrderService {
    List<Arrangement> findAll();

    Arrangement persist(Arrangement arrangement);

    Arrangement findById(Long id);

    List<Arrangement> processStock(List<Arrangement> arrangements);

    Mono<ByteArrayInputStream> rerportA(ReportTransactionARequest request);

    Mono<ByteArrayInputStream> rerportB();
}
