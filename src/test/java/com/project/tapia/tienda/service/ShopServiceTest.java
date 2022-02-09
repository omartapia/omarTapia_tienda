package com.project.tapia.tienda.service;

import com.project.tapia.tienda.controller.ClientController;
import com.project.tapia.tienda.controller.ShopController;
import com.project.tapia.tienda.models.Client;
import com.project.tapia.tienda.models.Shop;
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
public class ShopServiceTest {
    @Autowired
    private ShopController webClient;

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
        mockServer.when(HttpRequest.request().withMethod("GET")
                        .withPath("/shops/1")).
                respond(HttpResponse.response()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withDelay(TimeUnit.MILLISECONDS, 1000));

        // given: a new client
        Shop request = new Shop(1l,"tienda-1");

        // when: send a request
        ResponseEntity<Shop> response = webClient.find(1l);

        // then: response should be ok
        assertThat(response).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1l);
        assertThat(response.getBody().getName()).isEqualTo("tienda-1");

    }
}
