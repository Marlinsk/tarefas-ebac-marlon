package br.com.ebac.memelandia.memes.application.usecase;

import br.com.ebac.memelandia.memes.domain.entity.Meme;
import br.com.ebac.memelandia.memes.domain.exception.MemeNaoEncontradoException;
import br.com.ebac.memelandia.memes.domain.repository.MemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarMemeUseCase {
    private static final Logger logger = LoggerFactory.getLogger(BuscarMemeUseCase.class);

    private final MemeRepository memeRepository;

    public BuscarMemeUseCase(MemeRepository memeRepository) {
        this.memeRepository = memeRepository;
    }

    public Meme buscarPorId(Long id) {
        logger.info("Buscando meme por ID: {}", id);
        return memeRepository.buscarPorId(id).orElseThrow(() -> {
            logger.error("Meme não encontrado com ID: {}", id);
            return new MemeNaoEncontradoException(id);
        });
    }

    public List<Meme> buscarTodos() {
        logger.info("Buscando todos os memes");
        List<Meme> memes = memeRepository.buscarTodos();
        logger.info("Total de memes encontrados: {}", memes.size());
        return memes;
    }

    public List<Meme> buscarPorUsuarioId(Long usuarioId) {
        logger.info("Buscando memes do usuário: {}", usuarioId);
        List<Meme> memes = memeRepository.buscarPorUsuarioId(usuarioId);
        logger.info("Total de memes encontrados para usuário {}: {}", usuarioId, memes.size());
        return memes;
    }

    public List<Meme> buscarPorCategoriaId(Long categoriaId) {
        logger.info("Buscando memes da categoria: {}", categoriaId);
        List<Meme> memes = memeRepository.buscarPorCategoriaId(categoriaId);
        logger.info("Total de memes encontrados para categoria {}: {}", categoriaId, memes.size());
        return memes;
    }
}
