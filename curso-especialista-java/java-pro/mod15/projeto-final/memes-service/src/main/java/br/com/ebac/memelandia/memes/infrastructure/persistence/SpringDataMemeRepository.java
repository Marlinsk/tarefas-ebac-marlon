package br.com.ebac.memelandia.memes.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataMemeRepository extends JpaRepository<MemeEntity, Long> {
    List<MemeEntity> findByUsuarioId(Long usuarioId);
    List<MemeEntity> findByCategoriaId(Long categoriaId);
}
