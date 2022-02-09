package com.project.tapia.tienda.controller;

import com.project.tapia.tienda.models.Product;
import com.project.tapia.tienda.services.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@ControllerAdvice
public class ProductsController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<?>> findAll() {
        List<?> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> find(@PathVariable Long id) {
        Product product = service.findProductById(id);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping("/stock/{value}")
    public ResponseEntity<Product> saveStock(@Valid @PathVariable Integer value, @RequestBody Product product) {
        Product savedProduct = service.persistStock(product, value);
        return ResponseEntity.created(URI.create(String.format("/products/$s", savedProduct.getId()))).body(savedProduct);
    }

}
