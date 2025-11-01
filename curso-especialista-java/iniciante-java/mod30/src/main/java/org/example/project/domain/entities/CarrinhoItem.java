package org.example.project.domain.entities;

import org.example.project.common.annotations.BusinessKey;
import org.example.project.common.annotations.TableColumn;
import org.example.project.common.annotations.TableName;
import org.example.project.domain.model.BaseEntity;

import java.math.BigDecimal;
import java.util.Objects;

@TableName("CarrinhoItem")
public class CarrinhoItem extends BaseEntity {

    @BusinessKey("carrinho_id")
    @TableColumn(dbName = "carrinho_id", setJavaName = "setCarrinhoId")
    private Integer carrinhoId;

    @BusinessKey("produto_id")
    @TableColumn(dbName = "produto_id", setJavaName = "setProdutoId")
    private Integer produtoId;

    @TableColumn(dbName = "quantidade", setJavaName = "setQuantidade")
    private Integer quantidade;

    @TableColumn(dbName = "preco_unitario", setJavaName = "setPrecoUnitario")
    private BigDecimal precoUnitario;

    @TableColumn(dbName = "subtotal", setJavaName = "setSubtotal")
    private BigDecimal subtotal;

    public CarrinhoItem() { }

    public CarrinhoItem(Integer carrinhoId, Integer produtoId, Integer quantidade,
                        BigDecimal precoUnitario, BigDecimal subtotal) {
        this.carrinhoId = carrinhoId; this.produtoId = produtoId;
        this.quantidade = quantidade; this.precoUnitario = precoUnitario; this.subtotal = subtotal;
    }

    public Integer getCarrinhoId() {
        return carrinhoId;
    }

    public void setCarrinhoId(Integer carrinhoId) {
        this.carrinhoId = carrinhoId;
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
        return Objects.equals(this.carrinhoId, other.carrinhoId)
                && Objects.equals(this.produtoId, other.produtoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carrinhoId, produtoId);
    }

    @Override
    public String toString() {
        return String.format("{ id=%s, carrinhoId=%s, produtoId=%s, quantidade=%s, precoUnitario=%s, subtotal=%s, createdAt=%s, updatedAt=%s }", this.getId(), this.carrinhoId, this.produtoId, this.quantidade, this.precoUnitario, this.subtotal, this.getCreatedAt(), this.getUpdatedAt());
    }
}
