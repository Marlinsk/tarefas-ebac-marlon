package org.example.project.infra.dao.impl;

import jakarta.persistence.EntityManager;
import org.example.project.common.generics.JpaGenericDAO;
import org.example.project.domain.entities.CarrinhoItem;
import org.example.project.infra.dao.ICarrinhoItemDAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CarrinhoItemDAO extends JpaGenericDAO<CarrinhoItem, Integer> implements ICarrinhoItemDAO {

    public CarrinhoItemDAO(EntityManager entityManager) {
        super(entityManager, CarrinhoItem.class);
    }

    @Override
    public CarrinhoItem save(CarrinhoItem entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public CarrinhoItem update(CarrinhoItem entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<CarrinhoItem> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(CarrinhoItem.class, id));
    }

    @Override
    public boolean exists(Integer id) {
        return entityManager.find(CarrinhoItem.class, id) != null;
    }

    @Override
    public List<CarrinhoItem> findAll() {
        return entityManager.createQuery("select i from CarrinhoItem i order by i.id", CarrinhoItem.class).getResultList();
    }

    @Override
    public void deleteById(Integer id) {
        CarrinhoItem ref = entityManager.find(CarrinhoItem.class, id);
        if (ref != null) entityManager.remove(ref);
    }

    @Override
    public List<CarrinhoItem> findByCarrinho(Integer carrinhoId) {
        return entityManager.createQuery("select i from CarrinhoItem i where i.carrinho.id = :cid order by i.id", CarrinhoItem.class).setParameter("cid", carrinhoId).getResultList();
    }

    @Override
    public Optional<CarrinhoItem> findByCarrinhoAndProduto(Integer carrinhoId, Integer produtoId) {
        var list = entityManager.createQuery("select i from CarrinhoItem i where i.carrinho.id = :cid and i.produto.id = :pid", CarrinhoItem.class).setParameter("cid", carrinhoId).setParameter("pid", produtoId).setMaxResults(1).getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public void deleteByCarrinhoAndProduto(Integer carrinhoId, Integer produtoId) {
        entityManager.createQuery("delete from CarrinhoItem i where i.carrinho.id = :cid and i.produto.id = :pid").setParameter("cid", carrinhoId).setParameter("pid", produtoId).executeUpdate();
    }

    @Override
    public BigDecimal sumSubtotalByCarrinho(Integer carrinhoId) {
        BigDecimal total = entityManager.createQuery("select coalesce(sum(i.subtotal), 0) from CarrinhoItem i where i.carrinho.id = :cid", BigDecimal.class).setParameter("cid", carrinhoId).getSingleResult();
        return total != null ? total : BigDecimal.ZERO;
    }
}
