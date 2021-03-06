package com.project.tapia.tienda.controller;

import com.project.tapia.tienda.models.Arrangement;
import com.project.tapia.tienda.models.request.ReportTransactionARequest;
import com.project.tapia.tienda.services.IOrderService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
@ControllerAdvice
public class OrderController {

    @Autowired
    private IOrderService service;

    @GetMapping
    public ResponseEntity<List<?>> findAll() {
        List<?> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/make")
    public ResponseEntity<?> order(@RequestBody List<Arrangement> arrangements) {
        List<Arrangement> arrangementProcesses = service.processStock(arrangements);
        return ResponseEntity
                .created(
                        URI.create(String.format("/arrangements/")))
                .body(arrangementProcesses);
    }

    @PostMapping(value = "/transacciones/reporte-a",consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Mono<Resource>> reporteA(@Valid @RequestBody ReportTransactionARequest request) {
        String fileName = String.format("%s.csv", RandomStringUtils.randomAlphabetic(10));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .body(service.rerportA(request)
                        .flatMap(x -> {
                            Resource resource = new InputStreamResource(x);
                            return Mono.just(resource);
                        }));
    }

    @GetMapping("/transacciones/reporte-b")
    @ResponseBody
    public ResponseEntity<Mono<Resource>> reporteB() {
        String fileName = String.format("%s.csv", RandomStringUtils.randomAlphabetic(10));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .body(service.rerportB()
                        .flatMap(x -> {
                            Resource resource = new InputStreamResource(x);
                            return Mono.just(resource);
                        }));
    }
}
