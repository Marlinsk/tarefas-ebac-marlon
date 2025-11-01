package org.example.project.infra.dao;

import org.example.project.common.generics.IGenericDAO;
import org.example.project.domain.entities.Produto;

import java.util.Optional;

public interface IProdutoDAO extends IGenericDAO<Produto, Integer> {
    Optional<Produto> findByCodigo(String codigo);
}
