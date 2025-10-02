package org.example.project.domain.entities;

import jakarta.persistence.*;
import org.example.project.common.annotations.BusinessKey;
import org.example.project.domain.model.BaseEntity;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "CarrinhoItem", uniqueConstraints = @UniqueConstraint(name = "UK_CARRINHOITEM_CARRINHO_PRODUTO", columnNames = {"carrinho_id", "produto_id"}), indexes = {@Index(name = "IX_CARRINHOITEM_CARRINHO", columnList = "carrinho_id"), @Index(name = "IX_CARRINHOITEM_PRODUTO", columnList = "produto_id")})
public class CarrinhoItem extends BaseEntity {

    @BusinessKey("carrinho_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "carrinho_id", nullable = false, foreignKey = @ForeignKey(name = "FK_CARRINHOITEM_CARRINHO"))
    private Carrinho carrinho;

    @BusinessKey("produto_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name = "FK_CARRINHOITEM_PRODUTO"))
    private Produto produto;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", precision = 15, scale = 2, nullable = false)
    private BigDecimal precoUnitario;

    @Column(name = "subtotal", precision = 15, scale = 2, nullable = false)
    private BigDecimal subtotal;

    public CarrinhoItem() {}

    public CarrinhoItem(Integer carrinhoId, Integer produtoId, int quantidade, BigDecimal precoUnitario, BigDecimal subtotal) {}

    public CarrinhoItem(Carrinho carrinho, Produto produto, Integer quantidade, BigDecimal precoUnitario, BigDecimal subtotal) {
        this.carrinho = carrinho;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
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

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CarrinhoItem)) return false;
        CarrinhoItem other = (CarrinhoItem) o;
        return Objects.equals(this.carrinho != null ? this.carrinho.getId() : null, other.carrinho != null ? other.carrinho.getId() : null) && Objects.equals(this.produto != null ? this.produto.getId() : null, other.produto != null ? other.produto.getId() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.carrinho != null ? this.carrinho.getId() : null, this.produto != null ? this.produto.getId() : null);
    }

    @Override
    public String toString() {
        return String.format("{ id=%s, carrinhoId=%s, produtoId=%s, quantidade=%s, precoUnitario=%s, subtotal=%s, createdAt=%s, updatedAt=%s }", this.getId(), this.carrinho != null ? this.carrinho.getId() : null, this.produto != null ? this.produto.getId() : null, this.quantidade, this.precoUnitario, this.subtotal, this.getCreatedAt(), this.getUpdatedAt());
    }
}
