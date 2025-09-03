package org.example.mocks.dao;

import org.example.application.dao.interfaces.ICarrinhoDAO;
import org.example.application.domain.Carrinho;
import org.example.application.domain.Cliente;
import org.example.application.domain.Produto;
import org.example.application.domain.ProdutoQuantidade;
import org.example.application.domain.Venda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MockCarrinhoDAO extends BaseMockDAO<Carrinho, String> implements ICarrinhoDAO {

    @Override
    protected String getKey(Carrinho c) {
        return c.getCodigo();
    }

    @Override
    public void adicionarProduto(String codigoCarrinho, Produto produto, int quantidade) {
        Carrinho c = exigir(codigoCarrinho);
        c.adicionarAoCarrinho(produto, quantidade);
        atualizar(c);
    }

    @Override
    public void removerProduto(String codigoCarrinho, Produto produto, int quantidade) {
        Carrinho c = exigir(codigoCarrinho);
        c.removerProdutoDoCarrinho(produto, quantidade);
        atualizar(c);
    }

    @Override
    public BigDecimal calcularTotal(String codigoCarrinho) {
        return exigir(codigoCarrinho).getValorTotal();
    }

    @Override
    public Venda finalizarVenda(String codigoCarrinho, String codigoVenda) {
        Carrinho c = exigir(codigoCarrinho);
        if (c.getCliente() == null) throw new IllegalStateException("Carrinho sem cliente");
        if (c.getItens().isEmpty()) throw new IllegalStateException("Carrinho vazio");
        Venda v = c.finalizarVenda(codigoVenda);
        excluir(codigoCarrinho);
        return v;
    }

    //    public Carrinho criarCarrinho(Cliente cliente) {
    //        Objects.requireNonNull(cliente, "Cliente não pode ser nulo");
    //        Carrinho c = new Carrinho();
    //        c.setCliente(cliente);
    //        cadastrar(c);
    //        return c;
    //    }

    //    public void limparCarrinho(String codigoCarrinho) {
    //        Carrinho c = exigir(codigoCarrinho);
    //        List<ProdutoQuantidade> itens = new ArrayList<>(c.getItens());
    //        for (ProdutoQuantidade pq : itens) {
    //            c.removerProdutoDoCarrinho(pq.getProduto(), pq.getQuantidade());
    //        }
    //        atualizar(c);
    //    }

    private Carrinho exigir(String codigo) {
        Carrinho c = consultar(codigo);
        if (c == null) throw new IllegalStateException("Carrinho [" + codigo + "] não encontrado");
        return c;
    }
}
