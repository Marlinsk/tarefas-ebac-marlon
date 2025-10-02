package org.example.project.domain.entities;

import jakarta.persistence.*;
import org.example.project.common.annotations.BusinessKey;
import org.example.project.domain.model.BaseEntity;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "Produto", uniqueConstraints = @UniqueConstraint(name = "UK_PRODUTO_CODIGO", columnNames = "codigo"), indexes = {@Index(name = "IX_PRODUTO_CODIGO", columnList = "codigo"), @Index(name = "IX_PRODUTO_ATIVO", columnList = "ativo")})
public class Produto extends BaseEntity {

    @Column(name = "nome", length = 120, nullable = false)
    private String nome;

    @BusinessKey("codigo")
    @Column(name = "codigo", length = 30, nullable = false)
    private String codigo;

    @Column(name = "descricao", length = 500)
    private String descricao;

    @Column(name = "valor", precision = 15, scale = 2, nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", length = 40, nullable = false)
    private ProdutoCategoria produtoCategoria;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    public Produto() {}

    public Produto(String nome, String codigo, String descricao, BigDecimal valor, ProdutoCategoria produtoCategoria, Boolean ativo) {
        this.nome = nome;
        this.codigo = codigo;
        this.descricao = descricao;
        this.valor = valor;
        this.produtoCategoria = produtoCategoria;
        this.ativo = ativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public ProdutoCategoria getCategoria() {
        return produtoCategoria;
    }

    public void setCategoria(ProdutoCategoria produtoCategoria) {
        this.produtoCategoria = produtoCategoria;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Produto)) return false;
        Produto produto = (Produto) o;
        return Objects.equals(codigo, produto.codigo);
    }

    @Override
    public int hashCode() { return Objects.hash(codigo); }

    @Override
    public String toString() {
        return String.format("{ id=%s, nome=%s, codigo=%s, descricao=%s, valor=%s, categoria=%s, ativo=%s, createdAt=%s, updatedAt=%s }", this.getId(), this.nome, this.codigo, this.descricao, this.valor, this.produtoCategoria, this.ativo, this.getCreatedAt(), this.getUpdatedAt());
    }
}
