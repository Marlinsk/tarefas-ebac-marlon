package org.example.application.dao;

import org.example.application.dao.generic.GenericDAO;
import org.example.application.dao.interfaces.ICarrinhoDAO;
import org.example.application.dao.mapper.SingletonMap;
import org.example.application.domain.Carrinho;
import org.example.application.domain.Produto;
import org.example.application.domain.ProdutoQuantidade;
import org.example.application.domain.Venda;

import java.math.BigDecimal;
import java.util.*;

public class CarrinhoDAO extends GenericDAO<Carrinho, String> implements ICarrinhoDAO {

    @Override
    public void adicionarProduto(String codigoCarrinho, Produto produto, int quantidade) {
        Objects.requireNonNull(codigoCarrinho, "Código do carrinho não pode ser nulo");
        Objects.requireNonNull(produto, "Produto não pode ser nulo");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser positiva");
        Carrinho c = exigirCarrinho(codigoCarrinho);
        c.adicionarAoCarrinho(produto, quantidade);
        atualizar(c);
    }

    @Override
    public void removerProduto(String codigoCarrinho, Produto produto, int quantidade) {
        Objects.requireNonNull(codigoCarrinho, "Código do carrinho não pode ser nulo");
        Objects.requireNonNull(produto, "Produto não pode ser nulo");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser positiva");
        Carrinho c = exigirCarrinho(codigoCarrinho);
        c.removerProdutoDoCarrinho(produto, quantidade);
        atualizar(c);
    }

    @Override
    public BigDecimal calcularTotal(String codigoCarrinho) {
        Carrinho c = exigirCarrinho(codigoCarrinho);
        return c.getValorTotal();
    }

    @Override
    public Venda finalizarVenda(String codigoCarrinho, String codigoVenda) {
        Objects.requireNonNull(codigoVenda, "Código da venda não pode ser nulo");
        Carrinho c = exigirCarrinho(codigoCarrinho);

        if (c.getItens().isEmpty()) {
            throw new IllegalStateException("Não é possível finalizar venda: carrinho vazio");
        }

        if (c.getCliente() == null) {
            throw new IllegalStateException("Não é possível finalizar venda: carrinho sem cliente");
        }

        Venda v = c.finalizarVenda(codigoVenda);
        excluir(codigoCarrinho);
        return v;
    }

    public Carrinho obter(String codigoCarrinho) {
        return consultar(Objects.requireNonNull(codigoCarrinho, "Código do carrinho não pode ser nulo"));
    }

    public List<ProdutoQuantidade> listarItens(String codigoCarrinho) {
        Carrinho c = exigirCarrinho(codigoCarrinho);
        return new ArrayList<>(c.getItens());
    }

    private Carrinho exigirCarrinho(String codigo) {
        Carrinho c = obter(codigo);

        if (c == null) {
            throw new IllegalStateException("Carrinho com código [" + codigo + "] não encontrado");
        }

        return c;
    }

    public void limparStore() {
        Map<String, Carrinho> store = SingletonMap.getInstance().getTypedStore(Carrinho.class);
        store.clear();
    }
}
