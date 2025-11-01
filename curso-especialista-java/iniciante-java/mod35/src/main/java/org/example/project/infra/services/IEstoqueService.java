package org.example.project.infra.services;

import org.example.project.domain.entities.Estoque;

import java.util.List;
import java.util.Optional;

public interface IEstoqueService {
    Estoque criar(Estoque e);
    Estoque atualizar(Estoque e);
    Optional<Estoque> buscarPorId(Integer id);
    Optional<Estoque> buscarPorProduto(Integer produtoId);
    List<Estoque> listar();
    void remover(Integer id);

    void entrada(Integer produtoId, int qtd);
    void saida(Integer produtoId, int qtd);
}
