package br.com.ebac.memelandia.memes.infrastructure.controller;

import br.com.ebac.memelandia.memes.application.dto.MemeDTO;
import br.com.ebac.memelandia.memes.application.usecase.BuscarMemeUseCase;
import br.com.ebac.memelandia.memes.application.usecase.CriarMemeUseCase;
import br.com.ebac.memelandia.memes.domain.entity.Meme;
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
@RequestMapping("/api/memes")
public class MemeController {
    private static final Logger logger = LoggerFactory.getLogger(MemeController.class);

    private final CriarMemeUseCase criarMemeUseCase;
    private final BuscarMemeUseCase buscarMemeUseCase;
    private final Counter memeCriadoCounter;
    private final Counter memeBuscadoCounter;

    public MemeController(CriarMemeUseCase criarMemeUseCase, BuscarMemeUseCase buscarMemeUseCase, MeterRegistry meterRegistry) {
        this.criarMemeUseCase = criarMemeUseCase;
        this.buscarMemeUseCase = buscarMemeUseCase;
        this.memeCriadoCounter = Counter.builder("memes.criados").description("Total de memes criados").register(meterRegistry);
        this.memeBuscadoCounter = Counter.builder("memes.buscados").description("Total de buscas de memes").register(meterRegistry);
    }

    @PostMapping
    @Timed(value = "memes.create", description = "Tempo para criar meme")
    public ResponseEntity<MemeDTO> criarMeme(@Valid @RequestBody MemeDTO memeDTO) {
        logger.info("Recebida requisição para criar meme: {}", memeDTO.getNome());

        Meme meme = criarMemeUseCase.executar(memeDTO.getNome(), memeDTO.getDescricao(), memeDTO.getDataUrl(), memeDTO.getUsuarioId(), memeDTO.getCategoriaId());
        memeCriadoCounter.increment();

        logger.info("Meme criado com sucesso: {}", meme.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(MemeDTO.fromEntity(meme));
    }

    @GetMapping
    @Timed(value = "memes.list", description = "Tempo para listar memes")
    public ResponseEntity<List<MemeDTO>> listarMemes() {
        logger.info("Recebida requisição para listar todos os memes");

        List<Meme> memes = buscarMemeUseCase.buscarTodos();
        List<MemeDTO> memesDTO = memes.stream().map(MemeDTO::fromEntity).collect(Collectors.toList());

        logger.info("Retornando {} memes", memesDTO.size());
        return ResponseEntity.ok(memesDTO);
    }

    @GetMapping("/{id}")
    @Timed(value = "memes.findById", description = "Tempo para buscar meme por ID")
    public ResponseEntity<MemeDTO> buscarMemePorId(@PathVariable Long id) {
        logger.info("Recebida requisição para buscar meme por ID: {}", id);

        Meme meme = buscarMemeUseCase.buscarPorId(id);
        memeBuscadoCounter.increment();

        logger.info("Meme encontrado: {}", id);
        return ResponseEntity.ok(MemeDTO.fromEntity(meme));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Timed(value = "memes.findByUsuario", description = "Tempo para buscar memes por usuário")
    public ResponseEntity<List<MemeDTO>> buscarMemesPorUsuario(@PathVariable Long usuarioId) {
        logger.info("Recebida requisição para buscar memes do usuário: {}", usuarioId);

        List<Meme> memes = buscarMemeUseCase.buscarPorUsuarioId(usuarioId);
        List<MemeDTO> memesDTO = memes.stream().map(MemeDTO::fromEntity).collect(Collectors.toList());
        memeBuscadoCounter.increment();

        logger.info("Retornando {} memes do usuário {}", memesDTO.size(), usuarioId);
        return ResponseEntity.ok(memesDTO);
    }

    @GetMapping("/categoria/{categoriaId}")
    @Timed(value = "memes.findByCategoria", description = "Tempo para buscar memes por categoria")
    public ResponseEntity<List<MemeDTO>> buscarMemesPorCategoria(@PathVariable Long categoriaId) {
        logger.info("Recebida requisição para buscar memes da categoria: {}", categoriaId);

        List<Meme> memes = buscarMemeUseCase.buscarPorCategoriaId(categoriaId);
        List<MemeDTO> memesDTO = memes.stream().map(MemeDTO::fromEntity).collect(Collectors.toList());
        memeBuscadoCounter.increment();

        logger.info("Retornando {} memes da categoria {}", memesDTO.size(), categoriaId);
        return ResponseEntity.ok(memesDTO);
    }
}
