package com.project.tapia.tienda;

import com.project.tapia.tienda.controller.ClientController;
import com.project.tapia.tienda.services.IClientService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopApplicationTests {
	@Autowired
	private ClientController controller;

	@Autowired
	private IClientService service;

	@Test
	public void contextLoads() throws Exception {
		Assertions.assertThat(controller).isNotNull();
		Assertions.assertThat(service).isNotNull();
	}
}
