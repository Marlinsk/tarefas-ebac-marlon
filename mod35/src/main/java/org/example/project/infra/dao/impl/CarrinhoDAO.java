package org.example.project.infra.dao.impl;

import jakarta.persistence.EntityManager;
import org.example.project.common.generics.JpaGenericDAO;
import org.example.project.domain.entities.Carrinho;
import org.example.project.infra.dao.ICarrinhoDAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CarrinhoDAO extends JpaGenericDAO<Carrinho, Integer> implements ICarrinhoDAO {

    public CarrinhoDAO(EntityManager entityManager) {
        super(entityManager, Carrinho.class);
    }

    @Override
    public Carrinho save(Carrinho entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Carrinho update(Carrinho entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<Carrinho> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Carrinho.class, id));
    }

    @Override
    public boolean exists(Integer id) {
        return entityManager.find(Carrinho.class, id) != null;
    }

    @Override
    public List<Carrinho> findAll() {
        return entityManager.createQuery("select c from Carrinho c order by c.id", Carrinho.class).getResultList();
    }

    @Override
    public void deleteById(Integer id) {
        Carrinho ref = entityManager.find(Carrinho.class, id);
        if (ref != null) entityManager.remove(ref);
    }

    @Override
    public Optional<Carrinho> findByCodigo(String codigo) {
        List<Carrinho> list = entityManager.createQuery("select c from Carrinho c where c.codigo = :cod", Carrinho.class).setParameter("cod", codigo).setMaxResults(1).getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<Carrinho> findByCliente(Integer clienteId) {
        return entityManager.createQuery("select c from Carrinho c where c.cliente.id = :cid order by c.id", Carrinho.class).setParameter("cid", clienteId).getResultList();
    }

    @Override
    public void atualizarTotal(Integer carrinhoId, BigDecimal total) {
        entityManager.createQuery("update Carrinho c set c.total = :tot where c.id = :id").setParameter("tot", total).setParameter("id", carrinhoId).executeUpdate();
    }
}
