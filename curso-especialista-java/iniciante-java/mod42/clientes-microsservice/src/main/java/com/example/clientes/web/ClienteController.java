package com.example.clientes.web;

import com.example.clientes.services.ClienteService;
import com.example.clientes.web.dto.ClienteRequest;
import com.example.clientes.web.dto.ClienteResponse;
import com.example.clientes.web.dto.ClienteUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;

import static com.example.clientes.web.PageableUtils.sanitize;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private static final Set<String> ALLOWED_SORTS = Set.of("nome", "email", "cpf", "id");
    private static final String DEFAULT_QUERY = "page=0&size=10&sort=nome,asc";

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse criar(@RequestBody @Valid ClienteRequest request) {
        return this.service.criar(request);
    }

    @GetMapping({ "", "/" })
    public Object listar(@PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable, HttpServletRequest request) {
        if (request.getQueryString() != null && !request.getQueryString().isBlank()) {
            var safePageable = sanitize(pageable, ALLOWED_SORTS, Sort.by("nome").ascending());
            return ResponseEntity.ok(this.service.listar(safePageable));
        }

        String basePath = request.getRequestURI().replaceAll("/+$", "");
        URI location = URI.create(basePath + "?" + DEFAULT_QUERY);

        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(location).build();
    }

    @GetMapping("/{id}")
    public ClienteResponse obter(@PathVariable Long id) {
        return this.service.obter(id);
    }

    @PutMapping("/{id}")
    public ClienteResponse atualizar(@PathVariable Long id, @RequestBody @Valid ClienteUpdateRequest request) {
        return this.service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        this.service.deletar(id);
    }
}
