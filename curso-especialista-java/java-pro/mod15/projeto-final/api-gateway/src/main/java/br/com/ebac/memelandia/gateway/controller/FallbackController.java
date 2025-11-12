package br.com.ebac.memelandia.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/usuario")
    public ResponseEntity<Map<String, String>> usuarioFallback() {
        Map<String, String> response = new HashMap<>();
        response.put("erro", "Usuario Service está temporariamente indisponível");
        response.put("mensagem", "Por favor, tente novamente mais tarde");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @GetMapping("/categoria")
    public ResponseEntity<Map<String, String>> categoriaFallback() {
        Map<String, String> response = new HashMap<>();
        response.put("erro", "Categoria Service está temporariamente indisponível");
        response.put("mensagem", "Por favor, tente novamente mais tarde");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @GetMapping("/memes")
    public ResponseEntity<Map<String, String>> memesFallback() {
        Map<String, String> response = new HashMap<>();
        response.put("erro", "Memes Service está temporariamente indisponível");
        response.put("mensagem", "Por favor, tente novamente mais tarde");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
