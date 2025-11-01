package org.example.application.dao.interfaces;

import org.example.application.dao.generic.IGenericDAO;
import org.example.application.domain.Carrinho;
import org.example.application.domain.Produto;
import org.example.application.domain.Venda;

import java.math.BigDecimal;

public interface ICarrinhoDAO extends IGenericDAO<Carrinho, String> {
    void adicionarProduto(String codigoCarrinho, Produto produto, int quantidade);
    void removerProduto(String codigoCarrinho, Produto produto, int quantidade);
    BigDecimal calcularTotal(String codigoCarrinho);
    Venda finalizarVenda(String codigoCarrinho, String codigoVenda);
}
