package com.project.tapia.tienda.services;

import com.project.tapia.tienda.models.Inventory;
import com.project.tapia.tienda.models.Product;

import java.util.List;

public interface IInventoryService {

    List<Inventory> findAll();
    Inventory persist(Inventory inventory);
    Inventory findById(Long id);
    List<Inventory> findByShopAndProduct(List<Product> products, Long shop);
    List<Inventory> processStock(List<Product> products, Long shop, String identification);
}
