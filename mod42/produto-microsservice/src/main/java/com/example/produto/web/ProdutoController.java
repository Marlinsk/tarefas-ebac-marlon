package com.example.produto.web;

import com.example.produto.services.ProdutoService;
import com.example.produto.web.dto.ProdutoRequest;
import com.example.produto.web.dto.ProdutoResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.Set;

import static com.example.produto.web.PageableUtils.sanitize;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    private static final Set<String> ALLOWED_SORTS = Set.of("nome", "preco", "sku", "id");
    private static final String DEFAULT_QUERY = "page=0&size=10&sort=sku,asc";

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponse criar(@RequestBody ProdutoRequest request) {
        return service.criar(request);
    }

    @GetMapping({ "", "/" })
    public ResponseEntity<?> listar(@PageableDefault(page = 0, size = 10, sort = "sku", direction = Sort.Direction.ASC) Pageable pageable, HttpServletRequest request) {
        if (request.getQueryString() != null && !request.getQueryString().isBlank()) {
            var safePageable = sanitize(pageable, ALLOWED_SORTS, Sort.by("sku").ascending());
            return ResponseEntity.ok(service.listar(safePageable));
        }

        String basePath = request.getRequestURI().replaceAll("/+$", "");
        URI location = URI.create(basePath + "?" + DEFAULT_QUERY);

        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(location).build();
    }

    @GetMapping("/{id}")
    public ProdutoResponse obter(@PathVariable Long id) {
        return service.obter(id);
    }

    @PutMapping("/{id}")
    public ProdutoResponse atualizar(@PathVariable Long id, @RequestBody ProdutoRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
