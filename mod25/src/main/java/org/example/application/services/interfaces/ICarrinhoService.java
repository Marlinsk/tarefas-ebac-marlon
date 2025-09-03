package org.example.application.services.interfaces;

import org.example.application.domain.Carrinho;
import org.example.application.domain.Cliente;
import org.example.application.domain.Produto;
import org.example.application.domain.Venda;
import org.example.application.services.generic.IGenericService;

import java.math.BigDecimal;
import java.util.List;

public interface ICarrinhoService extends IGenericService<Carrinho, String> {
    Carrinho criarCarrinho(Cliente cliente);
    void adicionarProduto(String codigoCarrinho, Produto produto, int quantidade);
    void removerProduto(String codigoCarrinho, Produto produto, int quantidade);
    BigDecimal calcularTotal(String codigoCarrinho);
    Venda finalizarVenda(String codigoCarrinho, String codigoVenda);
    void limparCarrinho(String codigoCarrinho);
    void definirCliente(String codigoCarrinho, Cliente cliente);
    boolean contemProduto(String codigoCarrinho, Produto produto);
    int quantidadeTotalItens(String codigoCarrinho);
    List<?> listarItens(String codigoCarrinho);
}
