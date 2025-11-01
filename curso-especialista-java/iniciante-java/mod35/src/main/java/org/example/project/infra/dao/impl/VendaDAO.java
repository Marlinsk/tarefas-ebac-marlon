package org.example.project.infra.dao.impl;

import jakarta.persistence.EntityManager;
import org.example.project.common.generics.JpaGenericDAO;
import org.example.project.domain.entities.Venda;
import org.example.project.infra.dao.IVendaDAO;

import java.util.List;
import java.util.Optional;

public class VendaDAO extends JpaGenericDAO<Venda, Integer> implements IVendaDAO {

    public VendaDAO(EntityManager entityManager) {
        super(entityManager, Venda.class);
    }

    @Override
    public Venda save(Venda entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Venda update(Venda entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<Venda> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Venda.class, id));
    }

    @Override
    public boolean exists(Integer id) {
        return entityManager.find(Venda.class, id) != null;
    }

    @Override
    public List<Venda> findAll() {
        return entityManager.createQuery("select v from Venda v order by v.id", Venda.class).getResultList();
    }

    @Override
    public void deleteById(Integer id) {
        Venda ref = entityManager.find(Venda.class, id);
        if (ref != null) entityManager.remove(ref);
    }

    @Override
    public Optional<Venda> findByNumero(String numero) {
        var list = entityManager.createQuery("select v from Venda v where v.numero = :num", Venda.class).setParameter("num", numero).setMaxResults(1).getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<Venda> findByCliente(Integer clienteId) {
        return entityManager.createQuery("select v from Venda v where v.cliente.id = :cid order by v.id", Venda.class).setParameter("cid", clienteId).getResultList();
    }
    }
