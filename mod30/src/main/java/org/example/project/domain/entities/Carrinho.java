package org.example.project.domain.entities;

import org.example.project.common.annotations.BusinessKey;
import org.example.project.common.annotations.TableColumn;
import org.example.project.common.annotations.TableName;
import org.example.project.domain.model.BaseEntity;

import java.math.BigDecimal;
import java.util.Objects;

@TableName("Carrinho")
public class Carrinho extends BaseEntity {

    @BusinessKey("codigo")
    @TableColumn(dbName = "codigo", setJavaName = "setCodigo")
    private String codigo;

    @TableColumn(dbName = "cliente_id", setJavaName = "setClienteId")
    private Integer clienteId;

    @TableColumn(dbName = "status", setJavaName = "setStatus")
    private String status;

    @TableColumn(dbName = "total", setJavaName = "setTotal")
    private BigDecimal total;

    public Carrinho() { }

    public Carrinho(String codigo, Integer clienteId, String status, BigDecimal total) {
        this.codigo = codigo; this.clienteId = clienteId; this.status = status; this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
        return String.format("{ id=%s, codigo=%s, clienteId=%s, status=%s, total=%s, createdAt=%s, updatedAt=%s }", this.getId(), this.codigo, this.clienteId, this.status, this.total, this.getCreatedAt(), this.getUpdatedAt());
    }
}
