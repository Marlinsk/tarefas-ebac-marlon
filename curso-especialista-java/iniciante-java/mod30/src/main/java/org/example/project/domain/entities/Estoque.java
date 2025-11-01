package org.example.project.domain.entities;

import org.example.project.common.annotations.BusinessKey;
import org.example.project.common.annotations.TableColumn;
import org.example.project.common.annotations.TableName;
import org.example.project.domain.model.BaseEntity;

import java.util.Objects;

@TableName("Estoque")
public class Estoque extends BaseEntity {

    @BusinessKey(value = "produto_id")
    @TableColumn(dbName = "produto_id", setJavaName = "setProdutoId")
    private Integer produtoId;

    @TableColumn(dbName = "quantidade", setJavaName = "setQuantidade")
    private Integer quantidade;

    public Estoque() { }

    public Estoque(Integer produtoId, Integer quantidade) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Estoque)) return false;
        if (!super.equals(o)) return false;
        Estoque estoque = (Estoque) o;
        return Objects.equals(produtoId, estoque.produtoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), produtoId);
    }

    @Override
    public String toString() {
        return String.format("{ id=%s, produtoId=%s, quantidade=%s, createdAt=%s, updatedAt=%s }", this.getId(), this.produtoId, this.quantidade, this.getCreatedAt(), this.getUpdatedAt());
    }
}
