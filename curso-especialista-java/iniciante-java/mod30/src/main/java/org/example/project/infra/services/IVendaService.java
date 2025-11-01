package org.example.project.infra.services;

import org.example.project.domain.entities.Venda;

import java.util.List;
import java.util.Optional;

public interface IVendaService {
    Venda criar(Venda v);
    Venda atualizar(Venda v);
    Optional<Venda> buscarPorId(Integer id);
    Optional<Venda> buscarPorNumero(String numero);
    List<Venda> listar();
    List<Venda> listarPorCliente(Integer clienteId);
    void remover(Integer id);
}
