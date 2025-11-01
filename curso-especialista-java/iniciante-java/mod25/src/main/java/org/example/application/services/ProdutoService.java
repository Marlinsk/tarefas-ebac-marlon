package org.example.application.services;

import org.example.application.dao.interfaces.IProdutoDAO;
import org.example.application.domain.Produto;
import org.example.application.services.generic.GenericService;
import org.example.application.services.interfaces.IProdutoService;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoService extends GenericService<Produto, String> implements IProdutoService {

    private final IProdutoDAO produtoDAO;

    public ProdutoService(IProdutoDAO produtoDAO) {
        super(produtoDAO);
        this.produtoDAO = produtoDAO;
    }

    @Override
    public Produto buscarPorNomeExato(String nome) {
        return produtoDAO.buscarPorNomeExato(nome);
    }

    @Override
    public List<Produto> buscarPorNomeParcial(String nome) {
        return produtoDAO.buscarPorNomeParcial(nome);
    }

    @Override
    public List<Produto> buscarPorDescricao(String descricao) {
        return produtoDAO.buscarPorDescricao(descricao);
    }

    @Override
    public List<Produto> buscarPorFaixaDePreco(BigDecimal precoMin, BigDecimal precoMax) {
        return produtoDAO.buscarPorFaixaDePreco(precoMin, precoMax);
    }

    @Override
    public boolean existePorNome(String nome) {
        return produtoDAO.existePorNome(nome);
    }
}
