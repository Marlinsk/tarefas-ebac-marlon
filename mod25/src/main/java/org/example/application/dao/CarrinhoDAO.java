package org.example.application.dao;

import org.example.application.dao.generic.GenericDAO;
import org.example.application.dao.interfaces.ICarrinhoDAO;
import org.example.application.dao.mapper.SingletonMap;
import org.example.application.domain.Carrinho;
import org.example.application.domain.Cliente;
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
        c.adicionarProduto(produto, quantidade);
        atualizar(c);
    }

    @Override
    public void removerProduto(String codigoCarrinho, Produto produto, int quantidade) {
        Objects.requireNonNull(codigoCarrinho, "Código do carrinho não pode ser nulo");
        Objects.requireNonNull(produto, "Produto não pode ser nulo");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser positiva");
        Carrinho c = exigirCarrinho(codigoCarrinho);
        c.removerProduto(produto, quantidade);
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

    public Carrinho criarCarrinho(Cliente cliente) {
        Carrinho c = new Carrinho();
        c.setCliente(Objects.requireNonNull(cliente, "Cliente não pode ser nulo"));
        cadastrar(c);
        return c;
    }

    public Carrinho obter(String codigoCarrinho) {
        return consultar(Objects.requireNonNull(codigoCarrinho, "Código do carrinho não pode ser nulo"));
    }

    public void limparCarrinho(String codigoCarrinho) {
        Carrinho c = exigirCarrinho(codigoCarrinho);
        List<ProdutoQuantidade> itens = new ArrayList<>(c.getItens());

        for (ProdutoQuantidade pq : itens) {
            c.removerProduto(pq.getProduto(), pq.getQuantidade());
        }

        atualizar(c);
    }

    public void definirCliente(String codigoCarrinho, Cliente cliente) {
        Carrinho c = exigirCarrinho(codigoCarrinho);
        c.setCliente(Objects.requireNonNull(cliente, "Cliente não pode ser nulo"));
        atualizar(c);
    }

    public boolean contemProduto(String codigoCarrinho, Produto produto) {
        Carrinho c = exigirCarrinho(codigoCarrinho);
        Objects.requireNonNull(produto, "Produto não pode ser nulo");
        String cod = produto.getCodigo();
        return c.getItens().stream().anyMatch(pq -> pq.getProduto() != null && Objects.equals(cod, pq.getProduto().getCodigo()));
    }

    public int quantidadeTotalItens(String codigoCarrinho) {
        Carrinho c = exigirCarrinho(codigoCarrinho);
        return c.getItens().stream().mapToInt(ProdutoQuantidade::getQuantidade).sum();
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

    public int contar() {
        Map<String, Carrinho> store = SingletonMap.getInstance().getTypedStore(Carrinho.class);
        return store.size();
    }

    public Set<String> chaves() {
        Map<String, Carrinho> store = SingletonMap.getInstance().getTypedStore(Carrinho.class);
        return new LinkedHashSet<>(store.keySet());
    }

    public List<Carrinho> snapshot() {
        Map<String, Carrinho> store = SingletonMap.getInstance().getTypedStore(Carrinho.class);
        return new ArrayList<>(store.values());
    }
}
