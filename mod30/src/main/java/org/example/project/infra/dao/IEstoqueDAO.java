package org.example.project.infra.dao;

import org.example.project.common.generics.IGenericDAO;
import org.example.project.domain.entities.Estoque;

import java.util.Optional;

public interface IEstoqueDAO extends IGenericDAO<Estoque, Integer> {
    Optional<Estoque> findByProdutoId(Integer produtoId);
    void incrementar(Integer produtoId, int delta);
    void decrementar(Integer produtoId, int delta);
}

