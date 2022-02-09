package com.project.tapia.tienda.controller;

import com.project.tapia.tienda.models.Inventory;
import com.project.tapia.tienda.models.Product;
import com.project.tapia.tienda.services.impl.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private InventoryService service;

    @PostMapping("/{identification}/shop/{shop}")
    public ResponseEntity<?> order(@PathVariable String identification, @PathVariable Long shop, @RequestBody List<Product> products) {
        List<Inventory> invProducts = service.processStock(products, shop, identification);
        return ResponseEntity
                .created(
                        URI.create(String.format("/orders/$s", shop)))
                .body(invProducts
                        .stream()
                        .map(inv -> inv.getProduct())
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> find(@PathVariable Long id) {
        Inventory inventory = service.findById(id);
        return ResponseEntity.ok().body(inventory);
    }
}
