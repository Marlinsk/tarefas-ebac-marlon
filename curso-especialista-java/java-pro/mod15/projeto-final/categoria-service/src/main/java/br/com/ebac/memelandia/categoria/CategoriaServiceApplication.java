package br.com.ebac.memelandia.categoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CategoriaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoriaServiceApplication.class, args);
	}

}
