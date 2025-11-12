package br.com.ebac.memelandia.usuario.application.usecase;

import br.com.ebac.memelandia.usuario.application.dto.UsuarioDTO;
import br.com.ebac.memelandia.usuario.domain.entity.Usuario;
import br.com.ebac.memelandia.usuario.domain.exception.EmailJaCadastradoException;
import br.com.ebac.memelandia.usuario.domain.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CriarUsuarioUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CriarUsuarioUseCase.class);

    private final UsuarioRepository usuarioRepository;

    public CriarUsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public UsuarioDTO executar(UsuarioDTO usuarioDTO) {
        logger.info("Criando usuário com email: {}", usuarioDTO.getEmail());

        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            logger.warn("Tentativa de criar usuário com email já cadastrado: {}", usuarioDTO.getEmail());
            throw new EmailJaCadastradoException(usuarioDTO.getEmail());
        }

        Usuario usuario = Usuario.criar(usuarioDTO.getNome(), usuarioDTO.getEmail());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        logger.info("Usuário criado com sucesso. ID: {}", usuarioSalvo.getId());

        return UsuarioDTO.fromEntity(usuarioSalvo);
    }
}
