package br.com.ebac.memelandia.memes.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "categoria-service", url = "http://categoria-service:9002")
public interface CategoriaClient {

    @GetMapping("/api/categorias/{id}")
    CategoriaResponse buscarCategoriaPorId(@PathVariable Long id);
}
