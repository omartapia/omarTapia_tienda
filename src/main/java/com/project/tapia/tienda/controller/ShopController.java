package com.project.tapia.tienda.controller;

import com.project.tapia.tienda.models.Shop;
import com.project.tapia.tienda.services.IShopService;
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
@RequestMapping("/shops")
@ControllerAdvice
public class ShopController {
    @Autowired
    private IShopService service;

    @GetMapping
    public ResponseEntity<List<?>> findAll() {
        List<?> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shop> find(@PathVariable Long id) {
        try {
            Shop shop = service.findById(id);
            return ResponseEntity.ok().body(shop);
        }catch (Exception exception){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Shop> save(@Valid @RequestBody Shop shop) {
        Shop shopDb = service.save(shop);
        return ResponseEntity
                .created(URI.create(String.format("/clients/$s", shopDb.getId())))
                .body(shopDb);
    }
}
