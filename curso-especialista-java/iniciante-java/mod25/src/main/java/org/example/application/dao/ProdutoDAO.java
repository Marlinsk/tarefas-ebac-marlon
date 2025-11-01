package org.example.application.dao;

import org.example.application.dao.generic.GenericDAO;
import org.example.application.dao.interfaces.IProdutoDAO;
import org.example.application.dao.mapper.SingletonMap;
import org.example.application.domain.Produto;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ProdutoDAO extends GenericDAO<Produto, String> implements IProdutoDAO {

    @Override
    public Produto buscarPorNomeExato(String nome) {
        if (nome == null) return null;
        return buscarTodos().stream()
                .filter(p -> p.getNome() != null && p.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Produto> buscarPorNomeParcial(String nome) {
        if (nome == null) return List.of();
        return buscarTodos().stream()
                .filter(p -> p.getNome() != null && p.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Produto> buscarPorDescricao(String descricao) {
        if (descricao == null) return List.of();
        return buscarTodos().stream()
                .filter(p -> p.getDescricao() != null && p.getDescricao().toLowerCase().contains(descricao.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Produto> buscarPorFaixaDePreco(BigDecimal precoMin, BigDecimal precoMax) {
        if (precoMin == null || precoMax == null) return List.of();
        return buscarTodos().stream()
                .filter(p -> p.getValor() != null && p.getValor().compareTo(precoMin) >= 0 && p.getValor().compareTo(precoMax) <= 0)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorNome(String nome) {
        if (nome == null) return false;
        return buscarTodos().stream().anyMatch(p -> p.getNome() != null && p.getNome().equalsIgnoreCase(nome));
    }

    public void limparStore() {
        Map<String, Produto> store = SingletonMap.getInstance().getTypedStore(Produto.class);
        store.clear();
    }
}
