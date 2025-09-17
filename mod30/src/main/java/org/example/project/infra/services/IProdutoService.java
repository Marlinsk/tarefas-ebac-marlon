package org.example.project.infra.services;

import org.example.project.domain.entities.Produto;

import java.util.List;
import java.util.Optional;

public interface IProdutoService {
    Produto criar(Produto p);
    Produto atualizar(Produto p);
    Optional<Produto> buscarPorId(Integer id);
    Optional<Produto> buscarPorCodigo(String codigo);
    List<Produto> listar();
    void remover(Integer id);
}
