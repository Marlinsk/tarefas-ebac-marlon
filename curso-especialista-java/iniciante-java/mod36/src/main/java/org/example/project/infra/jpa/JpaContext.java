package org.example.project.infra.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public final class JpaContext implements AutoCloseable {

    private final EntityManagerFactory entityManagerFactory;
    private final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();

    public JpaContext(String persistenceUnitName) {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    public EntityManager getEntityManager() {
        EntityManager entityManager = entityManagerThreadLocal.get();
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = entityManagerFactory.createEntityManager();
            entityManagerThreadLocal.set(entityManager);
        }
        return entityManager;
    }

    public boolean isOpen() {
        EntityManager em = entityManagerThreadLocal.get();
        return em != null && em.isOpen();
    }

    public void begin() {
        EntityTransaction tx = getEntityManager().getTransaction();
        if (!tx.isActive()) tx.begin();
    }

    public void commit() {
        EntityTransaction tx = getEntityManager().getTransaction();
        if (tx.isActive()) tx.commit();
    }

    public void rollback() {
        EntityTransaction tx = getEntityManager().getTransaction();
        if (tx.isActive()) tx.rollback();
    }

    public void closeEntityManager() {
        EntityManager em = entityManagerThreadLocal.get();
        if (em != null && em.isOpen()) {
            em.close();
        }
        entityManagerThreadLocal.remove();
    }

    @Override
    public void close() {
        closeEntityManager();
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
