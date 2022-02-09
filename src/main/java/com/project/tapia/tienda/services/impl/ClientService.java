package com.project.tapia.tienda.services.impl;

import com.project.tapia.tienda.dao.IClientDao;
import com.project.tapia.tienda.exception.ApiWebClientException;
import com.project.tapia.tienda.models.Client;
import com.project.tapia.tienda.services.IClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private IClientDao dao;

    @Override
    public List<Client> findAll() {
        return dao.findAll();
    }

    @Override
    public Client findClientById(Long id) {
        return dao.findById(id)
                .orElseThrow(() -> new ApiWebClientException("client no found."));
    }

    @Override
    public Client persist(Client client) {
        return dao.save(client);
    }

    @Override
    public Client save(Client client) {
        return dao.save(client);
    }

    @Override
    public void delete(Long id) {
        dao.findById(id)
                .map(client ->{dao.delete(client); return client;})
                .orElseThrow(() -> new ApiWebClientException("client no found."));
        ;
    }
}
