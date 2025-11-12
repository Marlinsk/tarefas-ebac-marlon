package br.com.ebac.memelandia.categoria.application.usecase;

import br.com.ebac.memelandia.categoria.domain.entities.CategoriaMeme;
import br.com.ebac.memelandia.categoria.domain.exception.CategoriaNaoEncontradaException;
import br.com.ebac.memelandia.categoria.domain.repository.CategoriaMemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarCategoriaMemeUseCase {
    private static final Logger logger = LoggerFactory.getLogger(BuscarCategoriaMemeUseCase.class);

    private final CategoriaMemeRepository categoriaMemeRepository;

    public BuscarCategoriaMemeUseCase(CategoriaMemeRepository categoriaMemeRepository) {
        this.categoriaMemeRepository = categoriaMemeRepository;
    }

    public CategoriaMeme buscarPorId(Long id) {
        logger.info("Buscando categoria por ID: {}", id);
        return categoriaMemeRepository.buscarPorId(id)
                .orElseThrow(() -> {
                    logger.error("Categoria não encontrada com ID: {}", id);
                    return new CategoriaNaoEncontradaException(id);
                });
    }

    public List<CategoriaMeme> buscarTodas() {
        logger.info("Buscando todas as categorias");
        List<CategoriaMeme> categorias = categoriaMemeRepository.buscarTodas();
        logger.info("Total de categorias encontradas: {}", categorias.size());
        return categorias;
    }

    public List<CategoriaMeme> buscarPorUsuarioId(Long usuarioId) {
        logger.info("Buscando categorias para o usuário: {}", usuarioId);
        List<CategoriaMeme> categorias = categoriaMemeRepository.buscarPorUsuarioId(usuarioId);
        logger.info("Total de categorias encontradas para usuário {}: {}", usuarioId, categorias.size());
        return categorias;
    }
}
