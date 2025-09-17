package org.example.project.infra.services.impl;

import org.example.project.domain.entities.Produto;
import org.example.project.infra.dao.IProdutoDAO;
import org.example.project.infra.dao.impl.ProdutoDAO;
import org.example.project.infra.services.IProdutoService;

import java.util.List;
import java.util.Optional;

public class ProdutoService implements IProdutoService {

    private final IProdutoDAO dao;

    public ProdutoService(IProdutoDAO dao) {
        this.dao = dao;
    }

    public ProdutoService(String url, String user, String pass) {
        this.dao = new ProdutoDAO(url, user, pass);
    }

    @Override
    public Produto criar(Produto p) {
        return dao.save(p);
    }

    @Override
    public Produto atualizar(Produto p) {
        return dao.update(p);
    }

    @Override
    public Optional<Produto> buscarPorId(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Optional<Produto> buscarPorCodigo(String codigo) {
        return dao.findByCodigo(codigo);
    }

    @Override
    public List<Produto> listar() {
        return dao.findAll();
    }

    @Override
    public void remover(Integer id) {
        dao.deleteById(id);
    }
}
