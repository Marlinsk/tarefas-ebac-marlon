package br.com.ebac.memelandia.categoria.application.usecase;

import br.com.ebac.memelandia.categoria.domain.entities.CategoriaMeme;
import br.com.ebac.memelandia.categoria.domain.exception.UsuarioInvalidoException;
import br.com.ebac.memelandia.categoria.domain.repository.CategoriaMemeRepository;
import br.com.ebac.memelandia.categoria.infrastructure.config.UsuarioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CriarCategoriaMemeUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CriarCategoriaMemeUseCase.class);

    private final CategoriaMemeRepository categoriaMemeRepository;
    private final UsuarioClient usuarioClient;

    public CriarCategoriaMemeUseCase(CategoriaMemeRepository categoriaMemeRepository,
                                      UsuarioClient usuarioClient) {
        this.categoriaMemeRepository = categoriaMemeRepository;
        this.usuarioClient = usuarioClient;
    }

    @Transactional
    public CategoriaMeme executar(String nome, String descricao, Long usuarioId) {
        logger.info("Iniciando criação de categoria para usuário: {}", usuarioId);

        validarUsuario(usuarioId);

        CategoriaMeme categoriaMeme = CategoriaMeme.criar(nome, descricao, usuarioId);
        CategoriaMeme categoriaSalva = categoriaMemeRepository.salvar(categoriaMeme);

        logger.info("Categoria criada com sucesso. ID: {}, Nome: {}, Usuário: {}",
                   categoriaSalva.getId(), nome, usuarioId);
        return categoriaSalva;
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
}
