package org.example.project.infra.dao.impl;

import jakarta.persistence.EntityManager;
import org.example.project.common.generics.JpaGenericDAO;
import org.example.project.domain.entities.NotaFiscal;
import org.example.project.infra.dao.INotaFiscalDAO;

import java.util.List;
import java.util.Optional;

public class NotaFiscalDAO extends JpaGenericDAO<NotaFiscal, Integer> implements INotaFiscalDAO {

    public NotaFiscalDAO(EntityManager entityManager) {
        super(entityManager, NotaFiscal.class);
    }

    @Override
    public NotaFiscal save(NotaFiscal entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public NotaFiscal update(NotaFiscal entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<NotaFiscal> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(NotaFiscal.class, id));
    }

    @Override
    public boolean exists(Integer id) {
        return entityManager.find(NotaFiscal.class, id) != null;
    }

    @Override
    public List<NotaFiscal> findAll() {
        return entityManager.createQuery("select n from NotaFiscal n order by n.id", NotaFiscal.class).getResultList();
    }

    @Override
    public void deleteById(Integer id) {
        NotaFiscal ref = entityManager.find(NotaFiscal.class, id);
        if (ref != null) entityManager.remove(ref);
    }

    @Override
    public Optional<NotaFiscal> findByChaveAcesso(String chaveAcesso) {
        var list = entityManager.createQuery("select n from NotaFiscal n where n.chaveAcesso = :ch", NotaFiscal.class).setParameter("ch", chaveAcesso).setMaxResults(1).getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<NotaFiscal> findByVenda(Integer vendaId) {
        return entityManager.createQuery("select n from NotaFiscal n where n.venda.id = :vid order by n.id", NotaFiscal.class).setParameter("vid", vendaId).getResultList();
    }
}
