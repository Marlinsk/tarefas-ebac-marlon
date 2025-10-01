package org.example.project.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.project.domain.entities.Produto;

import java.util.List;

public class ProdutoDao {

    private final EntityManager entityManager;

    public ProdutoDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Produto salvar(Produto produto) {
        entityManager.persist(produto);
        return produto;
    }

    public Produto atualizar(Produto produto) {
        return entityManager.merge(produto);
    }

    public Produto buscarPorId(Long id) {
        return entityManager.find(Produto.class, id);
    }

    public Produto buscarPorCodigo(String codigo) {
        TypedQuery<Produto> q = entityManager.createQuery("select p from Produto p where p.codigo = :codigo", Produto.class);
        q.setParameter("codigo", codigo);
        List<Produto> list = q.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Produto> listarTodos() {
        return entityManager.createQuery("select p from Produto p order by p.id", Produto.class).getResultList();
    }

    public void remover(Produto produto) {
        Produto managed = entityManager.contains(produto) ? produto : entityManager.merge(produto);
        entityManager.remove(managed);
    }

    public long contar() {
        return entityManager.createQuery("select count(p) from Produto p", Long.class).getSingleResult();
    }
}
