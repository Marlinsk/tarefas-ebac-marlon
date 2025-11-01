package org.example.project.infra.services;

import org.example.project.domain.entities.CarrinhoItem;

import java.util.List;
import java.util.Optional;

public interface ICarrinhoItemService {
    CarrinhoItem criar(CarrinhoItem item);
    CarrinhoItem atualizar(CarrinhoItem item);
    Optional<CarrinhoItem> buscarPorId(Integer id);
    List<CarrinhoItem> listar();
    List<CarrinhoItem> listarPorCarrinho(Integer carrinhoId);
    Optional<CarrinhoItem> buscarPorCarrinhoEProduto(Integer carrinhoId, Integer produtoId);
    void remover(Integer id);
    void removerPorCarrinhoEProduto(Integer carrinhoId, Integer produtoId);
    java.math.BigDecimal totalDoCarrinho(Integer carrinhoId);
}