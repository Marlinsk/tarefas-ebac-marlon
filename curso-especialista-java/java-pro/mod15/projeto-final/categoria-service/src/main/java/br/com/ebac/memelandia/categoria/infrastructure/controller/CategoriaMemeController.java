package br.com.ebac.memelandia.categoria.infrastructure.controller;

import br.com.ebac.memelandia.categoria.application.dto.CategoriaMemeDTO;
import br.com.ebac.memelandia.categoria.application.usecase.BuscarCategoriaMemeUseCase;
import br.com.ebac.memelandia.categoria.application.usecase.CriarCategoriaMemeUseCase;
import br.com.ebac.memelandia.categoria.domain.entities.CategoriaMeme;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaMemeController {
    private static final Logger logger = LoggerFactory.getLogger(CategoriaMemeController.class);

    private final CriarCategoriaMemeUseCase criarCategoriaMemeUseCase;
    private final BuscarCategoriaMemeUseCase buscarCategoriaMemeUseCase;
    private final Counter categoriaCriadaCounter;
    private final Counter categoriaBuscadaCounter;

    public CategoriaMemeController(CriarCategoriaMemeUseCase criarCategoriaMemeUseCase, BuscarCategoriaMemeUseCase buscarCategoriaMemeUseCase, MeterRegistry meterRegistry) {
        this.criarCategoriaMemeUseCase = criarCategoriaMemeUseCase;
        this.buscarCategoriaMemeUseCase = buscarCategoriaMemeUseCase;
        this.categoriaCriadaCounter = Counter.builder("categorias.criadas").description("Total de categorias criadas").register(meterRegistry);
        this.categoriaBuscadaCounter = Counter.builder("categorias.buscadas").description("Total de buscas de categorias").register(meterRegistry);
    }

    @PostMapping
    @Timed(value = "categorias.create", description = "Tempo para criar categoria")
    public ResponseEntity<CategoriaMemeDTO> criarCategoria(@Valid @RequestBody CategoriaMemeDTO categoriaMemeDTO) {
        logger.info("Recebida requisição para criar categoria: {} do usuário: {}", categoriaMemeDTO.getNome(), categoriaMemeDTO.getUsuarioId());

        CategoriaMeme categoriaMeme = criarCategoriaMemeUseCase.executar(categoriaMemeDTO.getNome(), categoriaMemeDTO.getDescricao(), categoriaMemeDTO.getUsuarioId());
        categoriaCriadaCounter.increment();

        logger.info("Categoria criada com sucesso: {}", categoriaMeme.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaMemeDTO.fromEntity(categoriaMeme));
    }

    @GetMapping
    @Timed(value = "categorias.list", description = "Tempo para listar categorias")
    public ResponseEntity<List<CategoriaMemeDTO>> listarCategorias() {
        logger.info("Recebida requisição para listar todas as categorias");

        List<CategoriaMeme> categorias = buscarCategoriaMemeUseCase.buscarTodas();
        List<CategoriaMemeDTO> categoriasDTO = categorias.stream().map(CategoriaMemeDTO::fromEntity).collect(Collectors.toList());

        logger.info("Retornando {} categorias", categoriasDTO.size());
        return ResponseEntity.ok(categoriasDTO);
    }

    @GetMapping("/{id}")
    @Timed(value = "categorias.findById", description = "Tempo para buscar categoria por ID")
    public ResponseEntity<CategoriaMemeDTO> buscarCategoriaPorId(@PathVariable Long id) {
        logger.info("Recebida requisição para buscar categoria por ID: {}", id);

        CategoriaMeme categoriaMeme = buscarCategoriaMemeUseCase.buscarPorId(id);
        categoriaBuscadaCounter.increment();

        logger.info("Categoria encontrada: {}", id);
        return ResponseEntity.ok(CategoriaMemeDTO.fromEntity(categoriaMeme));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Timed(value = "categorias.findByUsuario", description = "Tempo para buscar categorias por usuário")
    public ResponseEntity<List<CategoriaMemeDTO>> buscarCategoriasPorUsuario(@PathVariable Long usuarioId) {
        logger.info("Recebida requisição para buscar categorias do usuário: {}", usuarioId);

        List<CategoriaMeme> categorias = buscarCategoriaMemeUseCase.buscarPorUsuarioId(usuarioId);
        List<CategoriaMemeDTO> categoriasDTO = categorias.stream().map(CategoriaMemeDTO::fromEntity).collect(Collectors.toList());

        categoriaBuscadaCounter.increment();

        logger.info("Retornando {} categorias para o usuário {}", categoriasDTO.size(), usuarioId);
        return ResponseEntity.ok(categoriasDTO);
    }
}
