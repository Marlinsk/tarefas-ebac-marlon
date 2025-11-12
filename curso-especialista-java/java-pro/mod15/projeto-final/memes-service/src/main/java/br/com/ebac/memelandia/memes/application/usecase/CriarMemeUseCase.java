package br.com.ebac.memelandia.memes.application.usecase;

import br.com.ebac.memelandia.memes.domain.entity.Meme;
import br.com.ebac.memelandia.memes.domain.exception.CategoriaInvalidaException;
import br.com.ebac.memelandia.memes.domain.exception.UsuarioInvalidoException;
import br.com.ebac.memelandia.memes.domain.repository.MemeRepository;
import br.com.ebac.memelandia.memes.infrastructure.client.CategoriaClient;
import br.com.ebac.memelandia.memes.infrastructure.client.UsuarioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CriarMemeUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CriarMemeUseCase.class);

    private final MemeRepository memeRepository;
    private final UsuarioClient usuarioClient;
    private final CategoriaClient categoriaClient;

    public CriarMemeUseCase(MemeRepository memeRepository, UsuarioClient usuarioClient, CategoriaClient categoriaClient) {
        this.memeRepository = memeRepository;
        this.usuarioClient = usuarioClient;
        this.categoriaClient = categoriaClient;
    }

    @Transactional
    public Meme executar(String nome, String descricao, String dataUrl, Long usuarioId, Long categoriaId) {
        logger.info("Iniciando criação de meme para usuário: {}, categoria: {}", usuarioId, categoriaId);

        validarUsuario(usuarioId);
        validarCategoria(categoriaId);

        Meme meme = Meme.criar(nome, descricao, dataUrl, usuarioId, categoriaId);
        Meme memeSalvo = memeRepository.salvar(meme);

        logger.info("Meme criado com sucesso. ID: {}, Nome: {}, Usuário: {}, Categoria: {}", memeSalvo.getId(), nome, usuarioId, categoriaId);
        return memeSalvo;
    }

    private void validarUsuario(Long usuarioId) {
        try {
            logger.debug("Validando existência do usuário com ID: {}", usuarioId);
            usuarioClient.buscarUsuarioPorId(usuarioId);
            logger.debug("Usuário validado com sucesso");
        } catch (Exception ex) {
            logger.error("Falha ao validar usuário com ID: {}", usuarioId, ex);
            throw new UsuarioInvalidoException(usuarioId);
        }
    }

    private void validarCategoria(Long categoriaId) {
        try {
            logger.debug("Validando existência da categoria com ID: {}", categoriaId);
            categoriaClient.buscarCategoriaPorId(categoriaId);
            logger.debug("Categoria validada com sucesso");
        } catch (Exception ex) {
            logger.error("Falha ao validar categoria com ID: {}", categoriaId, ex);
            throw new CategoriaInvalidaException(categoriaId);
        }
    }
}
