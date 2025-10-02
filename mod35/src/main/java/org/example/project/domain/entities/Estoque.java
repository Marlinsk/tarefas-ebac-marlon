package org.example.project.domain.entities;

import jakarta.persistence.*;
import org.example.project.common.annotations.BusinessKey;
import org.example.project.domain.model.BaseEntity;

import java.util.Objects;

@Entity
@Table(name = "Estoque", uniqueConstraints = @UniqueConstraint(name = "UK_ESTOQUE_PRODUTO", columnNames = "produto_id"), indexes = @Index(name = "IX_ESTOQUE_PRODUTO", columnList = "produto_id"))
public class Estoque extends BaseEntity {

    @BusinessKey("produto_id")
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "FK_ESTOQUE_PRODUTO"))
    private Produto produto;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    public Estoque() {}

    public Estoque(Produto produto, Integer quantidade) {
        this.produto = produto; this.quantidade = quantidade;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Estoque)) return false;
        Estoque estoque = (Estoque) o;
        return Objects.equals(produto != null ? produto.getId() : null, estoque.produto != null ? estoque.produto.getId() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produto != null ? produto.getId() : null);
    }

    @Override
    public String toString() {
        return String.format("{ id=%s, produtoId=%s, quantidade=%s, createdAt=%s, updatedAt=%s }", this.getId(), this.produto != null ? this.produto.getId() : null, this.quantidade, this.getCreatedAt(), this.getUpdatedAt());
    }
}
