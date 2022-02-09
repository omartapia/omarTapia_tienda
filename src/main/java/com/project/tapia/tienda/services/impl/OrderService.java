package com.project.tapia.tienda.services.impl;

import com.opencsv.CSVWriter;
import com.project.tapia.tienda.dao.IOrderDao;
import com.project.tapia.tienda.dao.IOrderDetailDao;
import com.project.tapia.tienda.exception.ApiWebClientException;
import com.project.tapia.tienda.models.Client;
import com.project.tapia.tienda.models.Order;
import com.project.tapia.tienda.models.OrderDetail;
import com.project.tapia.tienda.models.Product;
import com.project.tapia.tienda.models.Shop;
import com.project.tapia.tienda.services.IClientService;
import com.project.tapia.tienda.services.IOrderService;
import com.project.tapia.tienda.services.IProductService;
import com.project.tapia.tienda.services.IShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Value("${tienda.mock.stock.diez}")
    private String endPointStock10;

    @Value("${tienda.mock.stock.cinco}")
    private String endPointStock5;

    @Autowired
    private IOrderDao dao;

    @Autowired
    IOrderDetailDao orderDetailDao;

    @Autowired
    private WebClient webClient;

    @Autowired
    private IProductService productService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IShopService shopService;

    @Override
    public List<Order> findAll() {
        return dao.findAll();
    }

    @Override
    public Order persist(Order order) {
        return dao.save(order);
    }

    @Override
    public Order findById(Long id) {
        return this.dao.findById(id)
                .orElseThrow(() -> new ApiWebClientException("inventory no found."));
    }

    @Override
    public List<Order> processStock(List<Order> orders) {
        return orders.stream()
                .map(order -> {
                    Client clientModel = clientService.findClientByIdentification(order.getClient().getIdentificacion());
                    Shop shopModel = shopService.findById(order.getShop().getId());
                    List<OrderDetail> details = order.getDetails().stream().
                    map(detail -> {
                        Product productDb = productService.findProductById(detail.getProduct().getId());
                        detail.setTotal(productDb.getPrice() * detail.getQuantity());
                        stockVerification(productDb, detail.getQuantity());
                        detail.setProduct(productDb);
                        return  detail;
                    }).collect(Collectors.toList());
                    Order orderDb = dao.save(new Order(clientModel, shopModel, details, details.stream().mapToDouble(OrderDetail::getTotal).sum()));
                    return orderDb;
                }).collect(Collectors.toList());
    }

    private void stockVerification(Product product, Integer stockUser) {
        Integer currentStock = product.getStock() - stockUser;
        if(currentStock <= -10){
            throw new ApiWebClientException(String.format("Unidades no disponibles > 10, producto:%s",currentStock));
        } else if(currentStock > -10 && currentStock <=-5) {
            solicitarStock(product, currentStock, endPointStock10);
        }else if(currentStock > -5 && currentStock <= 0) {
            solicitarStock(product, currentStock, endPointStock5);
        }
    }

    private void solicitarStock(Product product, Integer currerntStock, String url) {
        webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    logger.error("Error endpoint with status code {}", clientResponse.statusCode());
                    throw new ApiWebClientException("No existe consumo de API externa");
                })
                .bodyToMono(Product.class)
                .subscribe(productSuscriber -> {
                    productService.persistStock(product,product.getStock() + currerntStock);
                });
    }

    @Override
    public Mono<ByteArrayInputStream> rerportA(){
        List<String[]> records = new ArrayList<>();
             records.add(new String[]{"shop", "dateTime", "transactions"});
             dao.findBTransactionReportA().forEach(transaction -> {
                records.add(new String[] {
                        transaction.getShop(),
                        transaction.getLocalDateTime().toString(),
                        transaction.getTransactions().toString()
                });
        });

        return Mono.just(buildCsv(records));
    }

    @Override
    public Mono<ByteArrayInputStream> rerportB(){
        List<String[]> records = new ArrayList<>();
        records.add(new String[]{"shop", "product", "total-price"});
        orderDetailDao.findBTransactionReportB().forEach(transaction -> {
            records.add(new String[] {
                    transaction.getShop(),
                    transaction.getProduct(),
                    transaction.getTotal().toString()
            });
        });

        return Mono.just(buildCsv(records));
    }

    public ByteArrayInputStream buildCsv(List records) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
        CSVWriter writer = new CSVWriter(streamWriter);
        records.forEach(o -> {
            writer.writeNext((String[]) o);
        });
        return new ByteArrayInputStream(stream.toByteArray());
    }
}
