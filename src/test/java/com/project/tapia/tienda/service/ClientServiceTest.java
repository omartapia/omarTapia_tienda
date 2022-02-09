package com.project.tapia.tienda.service;

import com.project.tapia.tienda.controller.ClientController;
import com.project.tapia.tienda.models.Client;
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
public class ClientServiceTest {
    @Autowired
    private ClientController webClient;

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
                        .withPath("/clients")).
                respond(HttpResponse.response()
                        .withBody("{ \"id\": 1, \"cod\": \"1000000008\", \"foto\": \"cliente-1.jpg\", \"nombre\": \"cliente-1\" }")
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withDelay(TimeUnit.MILLISECONDS, 1000));

        // given: a new client
        Client request = new Client(1l,"1000000008","cliente-1.jpg","cliente-1");

        // when: send a request
        ResponseEntity<Client> response = webClient.save(request);

        // then: response should be ok
        assertThat(response).isNotNull();
        assertThat(response.getBody().getIdentificacion()).isEqualTo("1000000008");
        assertThat(response.getBody().getFoto()).isEqualTo("cliente-1.jpg");
        assertThat(response.getBody().getNombre()).isEqualTo("cliente-1");

    }
}
