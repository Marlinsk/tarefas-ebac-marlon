package br.com.ebac.memelandia.usuario.domain.repository;

import br.com.ebac.memelandia.usuario.domain.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
