package br.com.ebac.memelandia.usuario.infrastructure.controller;

import br.com.ebac.memelandia.usuario.application.dto.UsuarioDTO;
import br.com.ebac.memelandia.usuario.application.usecase.BuscarUsuarioPorEmailUseCase;
import br.com.ebac.memelandia.usuario.application.usecase.BuscarUsuarioPorIdUseCase;
import br.com.ebac.memelandia.usuario.application.usecase.CriarUsuarioUseCase;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase;
    private final Counter usuarioCriadoCounter;

    public UsuarioController(CriarUsuarioUseCase criarUsuarioUseCase, BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase, BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase, MeterRegistry meterRegistry) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
        this.buscarUsuarioPorEmailUseCase = buscarUsuarioPorEmailUseCase;
        this.usuarioCriadoCounter = Counter.builder("usuarios.criados").description("Total de usuários criados").register(meterRegistry);
    }

    @PostMapping
    @Timed(value = "usuarios.create", description = "Tempo para criar usuário")
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        logger.info("Requisição recebida para criar usuário: {}", usuarioDTO.getEmail());

        UsuarioDTO usuarioCriado = criarUsuarioUseCase.executar(usuarioDTO);
        usuarioCriadoCounter.increment();

        logger.info("Usuário criado com sucesso: {}", usuarioCriado.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @GetMapping("/{id}")
    @Timed(value = "usuarios.get", description = "Tempo para buscar usuário por ID")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        logger.info("Requisição recebida para buscar usuário por ID: {}", id);

        UsuarioDTO usuario = buscarUsuarioPorIdUseCase.executar(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    @Timed(value = "usuarios.getByEmail", description = "Tempo para buscar usuário por email")
    public ResponseEntity<UsuarioDTO> buscarPorEmail(@PathVariable String email) {
        logger.info("Requisição recebida para buscar usuário por email: {}", email);

        UsuarioDTO usuario = buscarUsuarioPorEmailUseCase.executar(email);
        return ResponseEntity.ok(usuario);
    }
}
