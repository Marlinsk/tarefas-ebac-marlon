package org.example.project.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.project.domain.entities.Marca;

import java.util.List;

public class MarcaRepository implements ICrudRepository<Marca, Long> {

    private final EntityManager entityManager;

    public MarcaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Marca salvar(Marca e) {
        entityManager.persist(e);
        return e;
    }

    @Override
    public Marca atualizar(Marca e) {
        return entityManager.merge(e);
    }

    @Override
    public Marca buscarPorId(Long id) {
        return entityManager.find(Marca.class, id);
    }

    @Override
    public List<Marca> listarTodos() {
        return entityManager.createQuery("select m from Marca m order by m.id", Marca.class).getResultList();
    }

    @Override
    public void remover(Marca e) {
        entityManager.remove(entityManager.contains(e) ? e : entityManager.merge(e));
    }

    @Override
    public long contar() {
        return entityManager.createQuery("select count(m) from Marca m", Long.class).getSingleResult();
    }

    public Marca buscarPorNome(String nome) {
        TypedQuery<Marca> q = entityManager.createQuery("select m from Marca m where m.nome = :n", Marca.class);
        q.setParameter("n", nome);
        return q.getResultList().stream().findFirst().orElse(null);
    }
}
