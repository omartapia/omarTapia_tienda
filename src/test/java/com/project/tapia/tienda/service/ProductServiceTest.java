package com.project.tapia.tienda.service;

import com.project.tapia.tienda.controller.ClientController;
import com.project.tapia.tienda.controller.ProductsController;
import com.project.tapia.tienda.models.Client;
import com.project.tapia.tienda.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductsController webClient;

    private ClientAndServer mockServer;

    @BeforeEach
    public void startServer() {
        mockServer = ClientAndServer.startClientAndServer(8899);
    }

    @AfterEach
    public void stopServer() {
        mockServer.stop();
    }

    @Test
    void createShouldReturnTheResponse() {

        // set up mock server with a delay of 1 seconds
        mockServer.when(HttpRequest.request().withMethod("POST")
                        .withPath("/products/stock/{value}")).
                respond(HttpResponse.response()
                        .withBody("{ \"id\": 1, \"cod\": \"prod-1\", \"name\": \"prod-name-1\", \"price\": 5.5 , \"stock\":10}")
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withDelay(TimeUnit.MILLISECONDS, 1000));

        // given: a new client
        Product request = new Product(1l,"prod-1","prod-name-1", 5.5f,10);

        // when: send a request
        ResponseEntity<Product> response = webClient.saveStock(10,request);

        // then: response should be ok
        assertThat(response).isNotNull();
        assertThat(response.getBody().getCod()).isEqualTo("prod-1");
        assertThat(response.getBody().getName()).isEqualTo("prod-name-1");
        assertThat(response.getBody().getPrice()).isEqualTo(5.5f);
        assertThat(response.getBody().getStock()).isEqualTo(10);

    }
}
