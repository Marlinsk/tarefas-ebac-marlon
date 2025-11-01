package org.example.project.common.generics;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class JpaGenericDAO<T, ID extends Serializable> implements IGenericDAO<T, ID> {

    protected final EntityManager entityManager;
    protected final Class<T> entityClass;
    protected final String entityName; // evita problemas se @Entity(name="...")

    public JpaGenericDAO(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        this.entityName = entityClass.getSimpleName();
    }

    @Override
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public boolean exists(ID id) {
        return entityManager.find(entityClass, id) != null;
    }

    @Override
    public List<T> findAll() {
        final String jpql = "select e from " + entityName + " e";
        TypedQuery<T> q = entityManager.createQuery(jpql, entityClass);
        return q.getResultList();
    }

    @Override
    public void deleteById(ID id) {
        T ref = entityManager.find(entityClass, id);
        if (ref != null) {
            entityManager.remove(ref);
        }
    }
}
