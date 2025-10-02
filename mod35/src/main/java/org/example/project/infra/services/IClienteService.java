package org.example.project.infra.services;

import org.example.project.domain.entities.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    Cliente criar(Cliente c);
    Cliente atualizar(Cliente c);
    Optional<Cliente> buscarPorId(Integer id);
    List<Cliente> listar();
    void remover(Integer id);
}

