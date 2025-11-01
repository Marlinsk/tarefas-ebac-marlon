package org.example.builder;

import org.example.application.domain.Produto;

import java.math.BigDecimal;

public class ProdutoBuilder {
    private String codigo = "P1";
    private String nome = "Arroz";
    private String descricao = "Tipo 1";
    private BigDecimal valor = BigDecimal.valueOf(20.00);

    public static ProdutoBuilder umProduto() {
        return new ProdutoBuilder();
    }

    public ProdutoBuilder comCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public ProdutoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ProdutoBuilder comDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public ProdutoBuilder comValor(double v) {
        this.valor = BigDecimal.valueOf(v);
        return this;
    }

    public Produto build() {
        return new Produto(codigo,  nome, descricao, valor);
    }
}
