package br.com.ebac.memelandia.categoria.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataCategoriaMemeRepository extends JpaRepository<CategoriaMemeEntity, Long> {
    List<CategoriaMemeEntity> findByUsuarioId(Long usuarioId);
    boolean existsByNomeAndUsuarioId(String nome, Long usuarioId);
}
