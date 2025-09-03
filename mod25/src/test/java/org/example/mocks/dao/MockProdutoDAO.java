package org.example.mocks.dao;

import org.example.application.dao.interfaces.IProdutoDAO;
import org.example.application.domain.Produto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class MockProdutoDAO extends BaseMockDAO<Produto, String> implements IProdutoDAO {

    @Override
    protected String getKey(Produto p) {
        return p.getCodigo();
    }

    @Override
    public Produto buscarPorNomeExato(String nome) {
        if (nome == null) return null;
        return store.values().stream().filter(p -> p.getNome() != null && p.getNome().equalsIgnoreCase(nome)).findFirst().orElse(null);
    }

    @Override
    public List<Produto> buscarPorNomeParcial(String nome) {
        if (nome == null) return List.of();
        String n = nome.toLowerCase();
        return store.values().stream().filter(p -> p.getNome() != null && p.getNome().toLowerCase().contains(n)).collect(Collectors.toList());
    }

    @Override
    public List<Produto> buscarPorDescricao(String descricao) {
        if (descricao == null) return List.of();
        String d = descricao.toLowerCase();
        return store.values().stream().filter(p -> p.getDescricao() != null && p.getDescricao().toLowerCase().contains(d)).collect(Collectors.toList());
    }

    @Override
    public List<Produto> buscarPorFaixaDePreco(BigDecimal min, BigDecimal max) {
        if (min == null || max == null) return List.of();
        return store.values().stream().filter(p -> p.getValor() != null && p.getValor().compareTo(min) >= 0 && p.getValor().compareTo(max) <= 0).collect(Collectors.toList());
    }

    @Override
    public boolean existePorNome(String nome) {
        if (nome == null) return false;
        return store.values().stream().anyMatch(p -> p.getNome() != null && p.getNome().equalsIgnoreCase(nome));
    }
}
