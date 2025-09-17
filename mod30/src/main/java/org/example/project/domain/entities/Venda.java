package org.example.project.domain.entities;

import org.example.project.common.annotations.BusinessKey;
import org.example.project.common.annotations.TableColumn;
import org.example.project.common.annotations.TableName;
import org.example.project.domain.model.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@TableName("Venda")
public class Venda extends BaseEntity {

    @BusinessKey("numero")
    @TableColumn(dbName = "numero", setJavaName = "setNumero")
    private String numero;

    @TableColumn(dbName = "cliente_id", setJavaName = "setClienteId")
    private Integer clienteId;

    @TableColumn(dbName = "data_venda", setJavaName = "setDataVenda")
    private LocalDateTime dataVenda;

    @TableColumn(dbName = "total", setJavaName = "setTotal")
    private BigDecimal total;

    public Venda() { }

    public Venda(String numero, Integer clienteId, LocalDateTime dataVenda, BigDecimal total) {
        this.numero = numero;
        this.clienteId = clienteId;
        this.dataVenda = dataVenda;
        this.total = total;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Venda)) return false;
        if (!super.equals(o)) return false;
        Venda venda = (Venda) o;
        return Objects.equals(numero, venda.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numero);
    }

    @Override
    public String toString() {
        return String.format("{ id=%s, numero=%s, clienteId=%s, dataVenda=%s, total=%s, createdAt=%s, updatedAt=%s }", this.getId(), this.numero, this.clienteId, this.dataVenda, this.total, this.getCreatedAt(), this.getUpdatedAt());
    }
}
