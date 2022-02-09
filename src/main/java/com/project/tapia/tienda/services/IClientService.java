package com.project.tapia.tienda.services;

import com.project.tapia.tienda.models.Client;

import java.util.List;

public interface IClientService {

    List<Client> findAll();

    Client findClientById(Long id);

    Client persist(Client client);

    Client save(Client client);
    void delete(Long id);
}
