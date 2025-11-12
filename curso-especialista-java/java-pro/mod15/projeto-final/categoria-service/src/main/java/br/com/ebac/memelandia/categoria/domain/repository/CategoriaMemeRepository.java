package br.com.ebac.memelandia.categoria.domain.repository;

import br.com.ebac.memelandia.categoria.domain.entities.CategoriaMeme;

import java.util.List;
import java.util.Optional;

public interface CategoriaMemeRepository {
    CategoriaMeme salvar(CategoriaMeme categoriaMeme);
    Optional<CategoriaMeme> buscarPorId(Long id);
    List<CategoriaMeme> buscarTodas();
    List<CategoriaMeme> buscarPorUsuarioId(Long usuarioId);
    void deletar(Long id);
    boolean existePorNomeEUsuarioId(String nome, Long usuarioId);
}
