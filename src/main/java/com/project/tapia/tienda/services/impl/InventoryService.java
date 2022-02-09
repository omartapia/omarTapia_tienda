package com.project.tapia.tienda.services.impl;

import com.project.tapia.tienda.dao.IInventoryDao;
import com.project.tapia.tienda.exception.ApiWebClientException;
import com.project.tapia.tienda.models.Inventory;
import com.project.tapia.tienda.models.Product;
import com.project.tapia.tienda.services.IInventoryService;
import com.project.tapia.tienda.services.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService implements IInventoryService {
    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    @Value("${tienda.mock.stock.diez}")
    private String endPointStock10;

    @Value("${tienda.mock.stock.cinco}")
    private String endPointStock5;

    @Autowired
    private IInventoryDao dao;

    @Autowired
    private WebClient webClient;

    @Autowired
    private IProductService service;

    @Override
    public List<Inventory> findAll() {
        return dao.findAll();
    }

    @Override
    public Inventory persist(Inventory inventory) {
        return dao.save(inventory);
    }

    @Override
    public Inventory findById(Long id) {
        return this.dao.findById(id)
                .orElseThrow(() -> new ApiWebClientException("inventory no found."));
    }

    @Override
    public List<Inventory> findByShopAndProduct(List<Product> products, Long shop) {
        return dao.findByShopAndProduct(shop,
                        products.stream()
                                .map(Product::getId)
                                .collect(Collectors.toList()));
    }

    @Override
    public List<Inventory> processStock(List<Product> products, Long shop, String identification) {
        List<Inventory> inventories = findByShopAndProduct(products, shop)
                .stream()
                .map(inventory -> {
                    Integer stockInventory = inventory.getProduct().getStock();
                    products.stream()
                            .filter(product -> product.getCod().equalsIgnoreCase(inventory.getProduct().getCod()))
                            .findFirst()
                            .ifPresent(product -> {
                                Integer stock = stockInventory - product.getStock();

                                if(stock <= -10){
                                    throw new ApiWebClientException("Unidades no disponibles > 10");
                                } else if(stock > -10 && stock <=-5) {
                                     solicitarStock(inventory, stock, endPointStock10);
                                }else if(stock > -5 && stock <= 0) {
                                    solicitarStock(inventory, stock, endPointStock5);
                                }
                            });
                    return inventory;
                }).collect(Collectors.toList());

        return inventories;
    }

    private void solicitarStock(Inventory inventory, Integer stock, String url) {
        webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    logger.error("Error endpoint with status code {}", clientResponse.statusCode());
                    throw new ApiWebClientException("No existe consumo de API externa");
                })
                .bodyToMono(Product.class)
                .subscribe(product -> {
                    service.persistStock(inventory.getProduct(),product.getStock() + stock);
                });
    }

}
