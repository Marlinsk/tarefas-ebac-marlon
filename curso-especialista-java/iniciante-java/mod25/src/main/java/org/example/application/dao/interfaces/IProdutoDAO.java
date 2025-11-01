package org.example.application.dao.interfaces;

import org.example.application.dao.generic.IGenericDAO;
import org.example.application.domain.Produto;

import java.math.BigDecimal;
import java.util.List;

public interface IProdutoDAO extends IGenericDAO<Produto, String> {
    Produto buscarPorNomeExato(String nome);
    List<Produto> buscarPorNomeParcial(String nome);
    List<Produto> buscarPorDescricao(String descricao);
    List<Produto> buscarPorFaixaDePreco(BigDecimal precoMin, BigDecimal precoMax);
    boolean existePorNome(String nome);
}
