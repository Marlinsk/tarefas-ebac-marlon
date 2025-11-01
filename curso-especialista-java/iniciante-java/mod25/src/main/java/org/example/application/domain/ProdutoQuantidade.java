package org.example.application.domain;

import java.math.BigDecimal;

public class ProdutoQuantidade {

    private Produto produto;

    private Integer quantidade;

    private BigDecimal valorTotal;

    public ProdutoQuantidade() {
        this.quantidade = 0;
        this.valorTotal = BigDecimal.ZERO;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void adicionar(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        this.quantidade += quantidade;
        BigDecimal incremento = this.produto.getValor().multiply(BigDecimal.valueOf(quantidade));
        this.valorTotal = this.valorTotal.add(incremento);
    }

    public void remover(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        this.quantidade -= quantidade;
        if (this.quantidade < 0) this.quantidade = 0;

        BigDecimal decremento = this.produto.getValor().multiply(BigDecimal.valueOf(quantidade));
        this.valorTotal = this.valorTotal.subtract(decremento);
        if (this.valorTotal.signum() < 0) this.valorTotal = BigDecimal.ZERO;
    }
}
