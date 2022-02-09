package com.project.tapia.tienda.services;

import com.project.tapia.tienda.models.Product;
import com.project.tapia.tienda.models.response.ProductResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IProductService {

    Mono<ProductResponse> findMockProducts();

    List<Product> findAll();

    void persistProducts(List<Product> products);

    Product findProductById(Long id);

    Product persist(Product product);

    Product persistStock(Product product, Integer stock);

}
