package br.com.ebac.memelandia.usuario.application.usecase;

import br.com.ebac.memelandia.usuario.application.dto.UsuarioDTO;
import br.com.ebac.memelandia.usuario.domain.entity.Usuario;
import br.com.ebac.memelandia.usuario.domain.exception.UsuarioNaoEncontradoException;
import br.com.ebac.memelandia.usuario.domain.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BuscarUsuarioPorIdUseCase {
    private static final Logger logger = LoggerFactory.getLogger(BuscarUsuarioPorIdUseCase.class);

    private final UsuarioRepository usuarioRepository;

    public BuscarUsuarioPorIdUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTO executar(Long id) {
        logger.info("Buscando usuário por ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> {
            logger.error("Usuário não encontrado com ID: {}", id);
            return new UsuarioNaoEncontradoException(id);
        });

        return UsuarioDTO.fromEntity(usuario);
    }
}
