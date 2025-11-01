package org.example.project.infra.dao;

import org.example.project.common.generics.IGenericDAO;
import org.example.project.domain.entities.CarrinhoItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ICarrinhoItemDAO extends IGenericDAO<CarrinhoItem, Integer> {
    List<CarrinhoItem> findByCarrinho(Integer carrinhoId);
    Optional<CarrinhoItem> findByCarrinhoAndProduto(Integer carrinhoId, Integer produtoId);
    void deleteByCarrinhoAndProduto(Integer carrinhoId, Integer produtoId);
    BigDecimal sumSubtotalByCarrinho(Integer carrinhoId);
}
