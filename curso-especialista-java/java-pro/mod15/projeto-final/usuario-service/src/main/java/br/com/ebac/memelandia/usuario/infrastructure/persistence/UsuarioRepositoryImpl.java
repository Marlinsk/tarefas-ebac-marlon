package br.com.ebac.memelandia.usuario.infrastructure.persistence;

import br.com.ebac.memelandia.usuario.domain.entity.Usuario;
import br.com.ebac.memelandia.usuario.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final SpringDataUsuarioRepository springDataRepository;

    public UsuarioRepositoryImpl(SpringDataUsuarioRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = UsuarioEntity.fromDomain(usuario);
        UsuarioEntity savedEntity = springDataRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return springDataRepository.findById(id).map(UsuarioEntity::toDomain);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return springDataRepository.findByEmail(email).map(UsuarioEntity::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return springDataRepository.existsByEmail(email);
    }
}
