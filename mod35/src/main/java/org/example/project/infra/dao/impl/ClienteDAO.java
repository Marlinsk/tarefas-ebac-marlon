package org.example.project.infra.dao.impl;

import jakarta.persistence.EntityManager;
import org.example.project.common.generics.JpaGenericDAO;
import org.example.project.domain.entities.Cliente;
import org.example.project.infra.dao.IClienteDAO;

import java.util.List;
import java.util.Optional;

public class ClienteDAO extends JpaGenericDAO<Cliente, Integer> implements IClienteDAO {

    public ClienteDAO(EntityManager entityManager) {
        super(entityManager, Cliente.class);
    }

    @Override
    public Cliente save(Cliente e) {
        entityManager.persist(e);
        return e;
    }

    @Override
    public Cliente update(Cliente e) {
        return entityManager.merge(e);
    }

    @Override
    public Optional<Cliente> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Cliente.class, id));
    }

    @Override
    public boolean exists(Integer id) {
        return entityManager.find(Cliente.class, id) != null;
    }

    @Override
    public List<Cliente> findAll() {
        return entityManager.createQuery("select c from Cliente c order by c.id", Cliente.class).getResultList();
    }

    @Override
    public void deleteById(Integer id) {
        Cliente ref = entityManager.find(Cliente.class, id);
        if (ref != null) entityManager.remove(ref);
    }
}
