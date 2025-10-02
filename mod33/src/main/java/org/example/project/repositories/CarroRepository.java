package org.example.project.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.project.domain.entities.Carro;

import java.util.List;

public class CarroRepository implements ICrudRepository<Carro, Long> {

    private final EntityManager entityManager;

    public CarroRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Carro salvar(Carro e) {
        entityManager.persist(e);
        return e;
    }

    @Override
    public Carro atualizar(Carro e) {
        return entityManager.merge(e);
    }

    @Override
    public Carro buscarPorId(Long id) {
        return entityManager.find(Carro.class, id);
    }

    @Override
    public List<Carro> listarTodos() {
        return entityManager.createQuery("select c from Carro c order by c.id", Carro.class).getResultList();
    }

    @Override
    public void remover(Carro e) {
        entityManager.remove(entityManager.contains(e) ? e : entityManager.merge(e));
    }

    @Override
    public long contar() {
        return entityManager.createQuery("select count(c) from Carro c", Long.class).getSingleResult();
    }

    public List<Carro> buscarPorModelo(String modelo) {
        TypedQuery<Carro> q = entityManager.createQuery("select c from Carro c where c.modelo = :m", Carro.class);
        q.setParameter("m", modelo);
        return q.getResultList();
    }
}
