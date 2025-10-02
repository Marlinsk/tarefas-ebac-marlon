package org.example.project.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.project.domain.entities.Acessorio;

import java.util.List;

public class AcessorioRepository implements ICrudRepository<Acessorio, Long> {

    private final EntityManager entityManager;

    public AcessorioRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Acessorio salvar(Acessorio e) {
        entityManager.persist(e);
        return e;
    }

    @Override
    public Acessorio atualizar(Acessorio e) {
        return entityManager.merge(e);
    }

    @Override
    public Acessorio buscarPorId(Long id) {
        return entityManager.find(Acessorio.class, id);
    }

    @Override
    public List<Acessorio> listarTodos() {
        return entityManager.createQuery("select a from Acessorio a order by a.id", Acessorio.class).getResultList();
    }

    @Override
    public void remover(Acessorio e) {
        entityManager.remove(entityManager.contains(e) ? e : entityManager.merge(e));
    }

    @Override
    public long contar() {
        return entityManager.createQuery("select count(a) from Acessorio a", Long.class).getSingleResult();
    }

    public Acessorio buscarPorNome(String nome) {
        TypedQuery<Acessorio> q = entityManager.createQuery("select a from Acessorio a where a.nome = :n", Acessorio.class);
        q.setParameter("n", nome);
        return q.getResultList().stream().findFirst().orElse(null);
    }
}
