package br.com.ebac.memelandia.memes.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service", url = "http://usuario-service:9001")
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{id}")
    UsuarioResponse buscarUsuarioPorId(@PathVariable Long id);
}
