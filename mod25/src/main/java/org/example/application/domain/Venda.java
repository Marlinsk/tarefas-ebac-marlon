package org.example.application.domain;

import org.example.application.annotation.TipoChave;
import org.example.application.dao.interfaces.Persistente;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Venda implements Persistente {

    @TipoChave("getCodigo")
    private String codigo;

    private Cliente cliente;
    private LocalDateTime dataCriacao;
    private BigDecimal valorTotal;

    private final Map<String, ProdutoQuantidade> itens = new LinkedHashMap<>();

    public Venda(String codigo, Cliente cliente) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.dataCriacao = LocalDateTime.now();
        this.valorTotal = BigDecimal.ZERO;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public Collection<ProdutoQuantidade> getItens() {
        return Collections.unmodifiableCollection(itens.values());
    }

    public void adicionarProduto(Produto produto, int quantidade) {
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

    public void removerProduto(Produto produto, int quantidade) {
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

    public Nota emitirNota(String codigo, String serie, String chaveAcesso) {
        return new Nota(codigo, serie, chaveAcesso, LocalDateTime.now(), this.getValorTotal(), this);
    }

    @Override
    public String toString() {
        return "Venda{" + "codigo='" + codigo + '\'' + ", cliente=" + cliente + ", dataCriacao=" + dataCriacao + ", valorTotal=" + valorTotal + ", itens=" + itens + '}';
    }
}
