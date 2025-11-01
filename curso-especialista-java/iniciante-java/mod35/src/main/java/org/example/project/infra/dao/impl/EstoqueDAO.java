package org.example.project.infra.dao.impl;

import jakarta.persistence.EntityManager;
import org.example.project.common.generics.JpaGenericDAO;
import org.example.project.domain.entities.Estoque;
import org.example.project.infra.dao.IEstoqueDAO;

import java.util.List;
import java.util.Optional;

public class EstoqueDAO extends JpaGenericDAO<Estoque, Integer> implements IEstoqueDAO {

    public EstoqueDAO(EntityManager entityManager) {
        super(entityManager, Estoque.class);
    }

    @Override
    public Estoque save(Estoque entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Estoque update(Estoque entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<Estoque> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Estoque.class, id));
    }

    @Override
    public boolean exists(Integer id) {
        return entityManager.find(Estoque.class, id) != null;
    }

    @Override
    public List<Estoque> findAll() {
        return entityManager.createQuery("select e from Estoque e order by e.id", Estoque.class).getResultList();
    }

    @Override
    public void deleteById(Integer id) {
        Estoque ref = entityManager.find(Estoque.class, id);
        if (ref != null) entityManager.remove(ref);
    }

    @Override
    public Optional<Estoque> findByProdutoId(Integer produtoId) {
        var list = entityManager.createQuery("select e from Estoque e where e.produto.id = :pid", Estoque.class).setParameter("pid", produtoId).setMaxResults(1).getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public void incrementar(Integer produtoId, int delta) {
        entityManager.createQuery("update Estoque e set e.quantidade = e.quantidade + :d where e.produto.id = :pid").setParameter("d", delta).setParameter("pid", produtoId).executeUpdate();
    }

    @Override
    public void decrementar(Integer produtoId, int delta) {
        entityManager.createQuery("update Estoque e set e.quantidade = case when e.quantidade - :d < 0 then 0 else e.quantidade - :d end " + "where e.produto.id = :pid").setParameter("d", delta).setParameter("pid", produtoId).executeUpdate();
    }
}
