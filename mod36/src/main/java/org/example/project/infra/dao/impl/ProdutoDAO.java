package org.example.project.infra.dao.impl;

import jakarta.persistence.EntityManager;
import org.example.project.common.generics.JpaGenericDAO;
import org.example.project.domain.entities.Produto;
import org.example.project.infra.dao.IProdutoDAO;

import java.util.List;
import java.util.Optional;

public class ProdutoDAO extends JpaGenericDAO<Produto, Integer> implements IProdutoDAO {

    public ProdutoDAO(EntityManager entityManager) {
        super(entityManager, Produto.class);
    }

    @Override
    public Produto save(Produto e) {
        entityManager.persist(e);
        return e;
    }

    @Override
    public Produto update(Produto e) {
        return entityManager.merge(e);
    }

    @Override
    public Optional<Produto> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Produto.class, id));
    }

    @Override
    public boolean exists(Integer id) {
        return entityManager.find(Produto.class, id) != null;
    }

    @Override
    public List<Produto> findAll() {
        return entityManager.createQuery("select p from Produto p order by p.id", Produto.class).getResultList();
    }

    @Override
    public void deleteById(Integer id) {
        Produto ref = entityManager.find(Produto.class, id);
        if (ref != null) entityManager.remove(ref);
    }

    @Override
    public Optional<Produto> findByCodigo(String codigo) {
        var list = entityManager.createQuery("select p from Produto p where p.codigo = :cod", Produto.class).setParameter("cod", codigo).setMaxResults(1).getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }
}
