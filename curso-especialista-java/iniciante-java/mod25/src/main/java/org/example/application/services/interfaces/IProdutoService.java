package org.example.application.services.interfaces;

import org.example.application.domain.Produto;
import org.example.application.services.generic.IGenericService;

import java.math.BigDecimal;
import java.util.List;

public interface IProdutoService extends IGenericService<Produto, String> {
    Produto buscarPorNomeExato(String nome);
    List<Produto> buscarPorNomeParcial(String nome);
    List<Produto> buscarPorDescricao(String descricao);
    List<Produto> buscarPorFaixaDePreco(BigDecimal precoMin, BigDecimal precoMax);
    boolean existePorNome(String nome);
}
