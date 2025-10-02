package org.example.project.domain.entities;

import jakarta.persistence.*;
import org.example.project.common.annotations.BusinessKey;
import org.example.project.domain.model.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Venda", uniqueConstraints = @UniqueConstraint(name = "UK_VENDA_NUMERO", columnNames = "numero"), indexes = {@Index(name = "IX_VENDA_NUMERO", columnList = "numero"), @Index(name = "IX_VENDA_CLIENTE", columnList = "cliente_id")})
public class Venda extends BaseEntity {

    @BusinessKey("numero")
    @Column(name = "numero", length = 40, nullable = false)
    private String numero;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "FK_VENDA_CLIENTE"))
    private Cliente cliente;

    @Column(name = "data_venda", nullable = false)
    private LocalDateTime dataVenda;

    @Column(name = "total", precision = 15, scale = 2, nullable = false)
    private BigDecimal total;

    @OneToOne(mappedBy = "venda", fetch = FetchType.LAZY)
    private NotaFiscal notaFiscal;

    public Venda() {}

    public Venda(String numero, Cliente cliente, LocalDateTime dataVenda, BigDecimal total) {
        this.numero = numero;
        this.cliente = cliente;
        this.dataVenda = dataVenda;
        this.total = total;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Venda)) return false;
        Venda venda = (Venda) o;
        return Objects.equals(numero, venda.numero);
    }

    @Override
    public int hashCode() { return Objects.hash(numero); }

    @Override
    public String toString() {
        return String.format("{ id=%s, numero=%s, clienteId=%s, dataVenda=%s, total=%s, createdAt=%s, updatedAt=%s }", this.getId(), this.numero, this.cliente != null ? this.cliente.getId() : null, this.dataVenda, this.total, this.getCreatedAt(), this.getUpdatedAt());
    }
}
