package com.project.tapia.tienda.services;

import com.project.tapia.tienda.models.Client;
import com.project.tapia.tienda.models.Shop;

import java.util.List;

public interface IShopService {

    List<Shop> findAll();

    Shop findById(Long id);

    Shop save(Shop shop);
}
