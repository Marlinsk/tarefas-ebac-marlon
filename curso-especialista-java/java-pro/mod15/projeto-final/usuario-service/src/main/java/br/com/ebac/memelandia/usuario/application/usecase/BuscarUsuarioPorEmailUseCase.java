package br.com.ebac.memelandia.usuario.application.usecase;

import br.com.ebac.memelandia.usuario.application.dto.UsuarioDTO;
import br.com.ebac.memelandia.usuario.domain.entity.Usuario;
import br.com.ebac.memelandia.usuario.domain.exception.UsuarioNaoEncontradoException;
import br.com.ebac.memelandia.usuario.domain.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BuscarUsuarioPorEmailUseCase {
    private static final Logger logger = LoggerFactory.getLogger(BuscarUsuarioPorEmailUseCase.class);

    private final UsuarioRepository usuarioRepository;

    public BuscarUsuarioPorEmailUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTO executar(String email) {
        logger.info("Buscando usuário por email: {}", email);

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> {
            logger.error("Usuário não encontrado com email: {}", email);
            return new UsuarioNaoEncontradoException("Usuário não encontrado com email: " + email);
        });

        return UsuarioDTO.fromEntity(usuario);
    }
}
