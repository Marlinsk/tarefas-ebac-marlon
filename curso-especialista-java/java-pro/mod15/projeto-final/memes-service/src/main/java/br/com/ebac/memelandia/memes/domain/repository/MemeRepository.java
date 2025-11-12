package br.com.ebac.memelandia.memes.domain.repository;

import br.com.ebac.memelandia.memes.domain.entity.Meme;

import java.util.List;
import java.util.Optional;

public interface MemeRepository {
    Meme salvar(Meme meme);
    Optional<Meme> buscarPorId(Long id);
    List<Meme> buscarTodos();
    List<Meme> buscarPorUsuarioId(Long usuarioId);
    List<Meme> buscarPorCategoriaId(Long categoriaId);
    void deletar(Long id);
    boolean existePorId(Long id);
}
