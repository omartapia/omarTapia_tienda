package com.project.tapia.tienda;

import com.project.tapia.tienda.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Collectors;

@SpringBootApplication
public class TiendaApplication {

	@Autowired
	private IProductService service;

	public static void main(String[] args) {
		SpringApplication.run(TiendaApplication.class, args);
	}

	@Bean
	public void mockProducts() {
			service.findMockProducts()
					.map(productResponse ->
							productResponse.getProds()
									.stream()
									.collect(Collectors.toList()))
					.subscribe(service::persistProducts);
	}

}
