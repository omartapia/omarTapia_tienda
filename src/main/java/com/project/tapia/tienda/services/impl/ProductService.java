package com.project.tapia.tienda.services.impl;

import com.project.tapia.tienda.dao.IProductDao;
import com.project.tapia.tienda.exception.ApiWebClientException;
import com.project.tapia.tienda.models.Product;
import com.project.tapia.tienda.models.response.ProductResponse;
import com.project.tapia.tienda.services.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Min;
import java.util.List;

@Service
public class ProductService implements IProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Value("${tienda.mock.products}")
    private String mockProductsUri;

    @Autowired
    private WebClient webClient;

    @Autowired
    private IProductDao dao;

    @Override
    public Mono<ProductResponse> findMockProducts() {
        return webClient.get()
                .uri(mockProductsUri)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    logger.error("Error endpoint with status code {}", clientResponse.statusCode());
                    throw new ApiWebClientException("No existe consumo de API externa");
                })
                .bodyToMono(ProductResponse.class);
    }

    @Override
    public List<Product> findAll() {
        return dao.findAll();
    }

    @Override
    public void persistProducts(List<Product> products) {
        dao.saveAll(products);
    }

    @Override
    public Product persist(Product product) {
        return this.dao.save(product);
    }

    @Override
    public Product persistStock(Product product,
                                @Min(value = 0L, message = "El stock debe ser mayor que 0") Integer stock) {
        if(stock == null ) {

        }
        return dao.findById(product.getId())
                .map(prod -> {
                    prod.setStock(stock);
                    return this.dao.save(prod);
                }).orElseThrow(()-> new ApiWebClientException("product not found."));
    }

    @Override
    public Product findProductById(Long id) {
        return dao.findById(id)
                .orElseThrow(() -> new ApiWebClientException("product no found."));
    }
}
