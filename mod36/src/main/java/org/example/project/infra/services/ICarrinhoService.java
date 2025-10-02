package org.example.project.infra.services;

import org.example.project.domain.entities.Carrinho;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ICarrinhoService {
    Carrinho criar(Carrinho c);
    Carrinho atualizar(Carrinho c);
    Optional<Carrinho> buscarPorId(Integer id);
    Optional<Carrinho> buscarPorCodigo(String codigo);
    List<Carrinho> listar();
    List<Carrinho> listarPorCliente(Integer clienteId);
    void remover(Integer id);

    Carrinho adicionarItem(Integer carrinhoId, Integer produtoId, int quantidade, BigDecimal precoUnitario);
    Carrinho atualizarQuantidade(Integer carrinhoId, Integer produtoId, int novaQuantidade, BigDecimal precoUnitario);
    Carrinho removerItem(Integer carrinhoId, Integer produtoId);
}
