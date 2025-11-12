package br.com.ebac.memelandia.memes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MemesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemesServiceApplication.class, args);
	}

}
