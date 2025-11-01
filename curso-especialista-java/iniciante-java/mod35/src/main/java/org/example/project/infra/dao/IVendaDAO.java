package org.example.project.infra.dao;

import org.example.project.common.generics.IGenericDAO;
import org.example.project.domain.entities.Venda;

import java.util.List;
import java.util.Optional;

public interface IVendaDAO extends IGenericDAO<Venda, Integer> {
    Optional<Venda> findByNumero(String numero);
    List<Venda> findByCliente(Integer clienteId);
}
