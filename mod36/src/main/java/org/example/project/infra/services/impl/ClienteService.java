package org.example.project.infra.services.impl;

import jakarta.persistence.EntityManager;
import org.example.project.domain.entities.Cliente;
import org.example.project.infra.dao.IClienteDAO;
import org.example.project.infra.dao.impl.ClienteDAO;
import org.example.project.infra.services.IClienteService;

import java.util.List;
import java.util.Optional;

public class ClienteService implements IClienteService {

    private final IClienteDAO dao;

    public ClienteService(IClienteDAO dao) {
        this.dao = dao;
    }

    public ClienteService(EntityManager entityManager) {
        this.dao = new ClienteDAO(entityManager);
    }

    @Override
    public Cliente criar(Cliente c) {
        return dao.save(c);
    }

    @Override
    public Cliente atualizar(Cliente c) {
        return dao.update(c);
    }

    @Override
    public Optional<Cliente> buscarPorId(Integer id) {
        return dao.findById(id);
    }

    @Override
    public List<Cliente> listar() {
        return dao.findAll();
    }

    @Override
    public void remover(Integer id) {
        dao.deleteById(id);
    }
}
