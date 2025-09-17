package org.example.project.infra.services.impl;

import org.example.project.domain.entities.Estoque;
import org.example.project.infra.dao.IEstoqueDAO;
import org.example.project.infra.dao.impl.EstoqueDAO;
import org.example.project.infra.services.IEstoqueService;

import java.util.List;
import java.util.Optional;

public class EstoqueService implements IEstoqueService {

    private final IEstoqueDAO dao;

    public EstoqueService(IEstoqueDAO dao) {
        this.dao = dao;
    }

    public EstoqueService(String url, String user, String pass) {
        this.dao = new EstoqueDAO(url, user, pass);
    }

    @Override
    public Estoque criar(Estoque e) {
        return dao.save(e);
    }

    @Override
    public Estoque atualizar(Estoque e) {
        return dao.update(e);
    }

    @Override
    public Optional<Estoque> buscarPorId(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Optional<Estoque> buscarPorProduto(Integer produtoId) {
        return dao.findByProdutoId(produtoId);
    }

    @Override
    public List<Estoque> listar() {
        return dao.findAll();
    }

    @Override
    public void remover(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public void entrada(Integer produtoId, int qtd) {
        dao.incrementar(produtoId, qtd);
    }

    @Override
    public void saida(Integer produtoId, int qtd) {
        dao.decrementar(produtoId, qtd);
    }
}
