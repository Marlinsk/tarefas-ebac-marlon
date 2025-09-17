package org.example.project.domain.entities;

import org.example.project.common.annotations.BusinessKey;
import org.example.project.common.annotations.TableColumn;
import org.example.project.common.annotations.TableName;
import org.example.project.domain.model.BaseEntity;

import java.math.BigDecimal;
import java.util.Objects;

@TableName("Produto")
public class Produto extends BaseEntity {

    @TableColumn(dbName = "nome", setJavaName = "setNome")
    private String nome;

    @BusinessKey(value = "codigo")
    @TableColumn(dbName = "codigo", setJavaName = "setCodigo")
    private String codigo;

    @TableColumn(dbName = "descricao", setJavaName = "setDescricao")
    private String descricao;

    @TableColumn(dbName = "valor", setJavaName = "setValor")
    private BigDecimal valor;

    public Produto() {}

    public Produto(String nome, String codigo, String descricao, BigDecimal valor) {
        this.nome = nome;
        this.codigo = codigo;
        this.descricao = descricao;
        this.valor = valor;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Produto)) return false;
        if (!super.equals(o)) return false;
        Produto produto = (Produto) o;
        return Objects.equals(codigo, produto.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), codigo);
    }

    @Override
    public String toString() {
        return String.format("{ id=%s, nome=%s, codigo=%s, descricao=%s, valor=%s, createdAt=%s, updatedAt=%s }", this.getId(), this.nome, this.codigo, this.descricao, this.valor, this.getCreatedAt(), this.getUpdatedAt());
    }
}
