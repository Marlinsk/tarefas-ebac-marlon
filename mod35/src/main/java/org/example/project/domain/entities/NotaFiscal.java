package org.example.project.domain.entities;

import jakarta.persistence.*;
import org.example.project.common.annotations.BusinessKey;
import org.example.project.domain.model.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "NotaFiscal", uniqueConstraints = {@UniqueConstraint(name = "UK_NOTAFISCAL_CHAVE", columnNames = "chave_acesso"), @UniqueConstraint(name = "UK_NOTAFISCAL_VENDA", columnNames = "venda_id")}, indexes = {@Index(name = "IX_NOTAFISCAL_CHAVE", columnList = "chave_acesso"), @Index(name = "IX_NOTAFISCAL_VENDA", columnList = "venda_id")})
public class NotaFiscal extends BaseEntity {

    @BusinessKey("chave_acesso")
    @Column(name = "chave_acesso", length = 60, nullable = false)
    private String chaveAcesso;

    @Column(name = "numero", length = 30, nullable = false)
    private String numero;

    @Column(name = "serie", length = 10, nullable = false)
    private String serie;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "FK_NOTAFISCAL_VENDA"))
    private Venda venda;

    @Column(name = "data_emissao", nullable = false)
    private LocalDateTime dataEmissao;

    @Column(name = "valor_total", precision = 15, scale = 2, nullable = false)
    private BigDecimal valorTotal;

    public NotaFiscal() {}

    public NotaFiscal(String chaveAcesso, String numero, String serie, Venda venda, LocalDateTime dataEmissao, BigDecimal valorTotal) {
        this.chaveAcesso = chaveAcesso;
        this.numero = numero;
        this.serie = serie;
        this.venda = venda;
        this.dataEmissao = dataEmissao;
        this.valorTotal = valorTotal;
    }

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NotaFiscal)) return false;
        NotaFiscal nf = (NotaFiscal) o;
        return Objects.equals(chaveAcesso, nf.chaveAcesso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chaveAcesso);
    }

    @Override
    public String toString() {
        return String.format("{ id=%s, chaveAcesso=%s, numero=%s, serie=%s, vendaId=%s, dataEmissao=%s, valorTotal=%s, createdAt=%s, updatedAt=%s }", this.getId(), this.chaveAcesso, this.numero, this.serie, this.venda != null ? this.venda.getId() : null, this.dataEmissao, this.valorTotal, this.getCreatedAt(), this.getUpdatedAt());
    }
}
