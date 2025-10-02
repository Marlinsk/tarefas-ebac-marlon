package org.example.project.infra.dao;

import org.example.project.common.generics.IGenericDAO;
import org.example.project.domain.entities.Carrinho;

import java.util.List;
import java.util.Optional;

public interface ICarrinhoDAO extends IGenericDAO<Carrinho, Integer> {
    Optional<Carrinho> findByCodigo(String codigo);
    List<Carrinho> findByCliente(Integer clienteId);
    void atualizarTotal(Integer carrinhoId, java.math.BigDecimal total);
}
