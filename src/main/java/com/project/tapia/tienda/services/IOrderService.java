package com.project.tapia.tienda.services;

import com.project.tapia.tienda.models.Inventory;
import com.project.tapia.tienda.models.Product;

import java.util.List;

public interface OrderService {
    List<Inventory> processStock(List<Product> products, Long shop, String identification);

}
