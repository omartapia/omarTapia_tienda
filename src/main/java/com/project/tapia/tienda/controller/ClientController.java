package com.project.tapia.tienda.controller;

import com.project.tapia.tienda.models.Client;
import com.project.tapia.tienda.models.Product;
import com.project.tapia.tienda.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private IClientService service;

    @GetMapping
    public ResponseEntity<List<?>> findAll() {
        List<?> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> find(@PathVariable Long id) {
        Client client = service.findClientById(id);
        return ResponseEntity.ok().body(client);
    }

    @PostMapping
    public ResponseEntity<Client> save(@Valid @RequestBody Client client) {
        Client clientDb = service.save(client);
        return ResponseEntity
                .created(URI.create(String.format("/clients/$s", clientDb.getId())))
                .body(clientDb);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client client) {
        client.setId(id);
        Client savedClient = service.persist(client);
        return ResponseEntity
                .created(URI.create(String.format("/clients/$s", savedClient.getId())))
                .body(savedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body("Deleted successfully...!");
    }

}
