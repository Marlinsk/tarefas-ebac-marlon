package org.example.project.domain.entities;

import jakarta.persistence.*;
import org.example.project.common.annotations.BusinessKey;
import org.example.project.domain.model.BaseEntity;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Carrinho", uniqueConstraints = @UniqueConstraint(name = "UK_CARRINHO_CODIGO", columnNames = "codigo"), indexes = {@Index(name = "IX_CARRINHO_CODIGO", columnList = "codigo"), @Index(name = "IX_CARRINHO_CLIENTE", columnList = "cliente_id")})
public class Carrinho extends BaseEntity {

    @BusinessKey("codigo")
    @Column(name = "codigo", length = 50, nullable = false)
    private String codigo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "FK_CARRINHO_CLIENTE"))
    private Cliente cliente;

    @Column(name = "status", length = 30, nullable = false)
    private String status;

    @Column(name = "total", precision = 15, scale = 2, nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "carrinho", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Set<CarrinhoItem> itens = new LinkedHashSet<>();

    public Carrinho() {}

    public Carrinho(String codigo, Cliente cliente, String status, BigDecimal total) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.status = status;
        this.total = total;
    }

    public void addItem(CarrinhoItem item) {
        itens.add(item);
        item.setCarrinho(this);
    }

    public void removeItem(CarrinhoItem item) {
        itens.remove(item);
        item.setCarrinho(null);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Set<CarrinhoItem> getItens() {
        return itens;
    }

    public void setItens(Set<CarrinhoItem> itens) {
        this.itens = itens;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Carrinho)) return false;
        Carrinho other = (Carrinho) o;
        return Objects.equals(this.codigo, other.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return String.format("{ id=%s, codigo=%s, cliente=%s, status=%s, total=%s, createdAt=%s, updatedAt=%s }", this.getId(), this.codigo, this.cliente != null ? this.cliente.getId() : null, this.status, this.total, this.getCreatedAt(), this.getUpdatedAt());
    }
}
