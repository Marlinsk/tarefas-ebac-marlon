package org.example.application.domain;

import org.example.application.annotation.TipoChave;
import org.example.application.dao.interfaces.Persistente;

import java.math.BigDecimal;
import java.util.*;

public class Carrinho implements Persistente {

    @TipoChave("getCodigo")
    private String codigo;
    private Cliente cliente;
    private final Map<String, ProdutoQuantidade> itens = new LinkedHashMap<>();
    private BigDecimal valorTotal = BigDecimal.ZERO;

    public Carrinho() {
        this.codigo = UUID.randomUUID().toString();
    }

    public Carrinho(Cliente cliente, BigDecimal valorTotal) {
        this.cliente = cliente;
        this.valorTotal = valorTotal;
    }

    public String getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public Collection<ProdutoQuantidade> getItens() {
        return Collections.unmodifiableCollection(itens.values());
    }

    public void adicionarAoCarrinho(Produto produto, int quantidade) {
        if (produto == null) throw new IllegalArgumentException("Produto não pode ser nulo");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser positiva");

        String key = produto.getCodigo();
        ProdutoQuantidade pq = itens.get(key);
        if (pq == null) {
            pq = new ProdutoQuantidade();
            pq.setProduto(produto);
            itens.put(key, pq);
        }
        pq.adicionar(quantidade);
        recalcularTotal();
    }

    public void removerProdutoDoCarrinho(Produto produto, int quantidade) {
        if (produto == null) throw new IllegalArgumentException("Produto não pode ser nulo");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser positiva");

        String key = produto.getCodigo();
        ProdutoQuantidade pq = itens.get(key);
        if (pq == null) return;

        pq.remover(quantidade);
        if (pq.getQuantidade() <= 0) {
            itens.remove(key);
        }
        recalcularTotal();
    }

    private void recalcularTotal() {
        this.valorTotal = itens.values().stream().map(ProdutoQuantidade::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Venda finalizarVenda(String codigoVenda) {
        Venda venda = new Venda(codigoVenda, cliente);
        for (ProdutoQuantidade pq : itens.values()) {
            venda.registrarProdutoNaVenda(pq.getProduto(), pq.getQuantidade());
        }
        return venda;
    }

    public String toString() {
        return "Carrinho{codigo='" + codigo + '\'' + ", cliente=" + (cliente != null ? cliente.getNome() : "null") + ", valorTotal=" + valorTotal + ", itens=" + itens.size() + '}';
    }
}
