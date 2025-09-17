package org.example.project.domain.entities;

import org.example.project.common.annotations.BusinessKey;
import org.example.project.common.annotations.TableColumn;
import org.example.project.common.annotations.TableName;
import org.example.project.domain.model.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@TableName("NotaFiscal")
public class NotaFiscal extends BaseEntity {

    @BusinessKey("chave_acesso")
    @TableColumn(dbName = "chave_acesso", setJavaName = "setChaveAcesso")
    private String chaveAcesso;

    @TableColumn(dbName = "numero", setJavaName = "setNumero")
    private String numero;

    @TableColumn(dbName = "serie", setJavaName = "setSerie")
    private String serie;

    @TableColumn(dbName = "venda_id", setJavaName = "setVendaId")
    private Integer vendaId;

    @TableColumn(dbName = "data_emissao", setJavaName = "setDataEmissao")
    private LocalDateTime dataEmissao;

    @TableColumn(dbName = "valor_total", setJavaName = "setValorTotal")
    private BigDecimal valorTotal;

    public NotaFiscal() { }

    public NotaFiscal(String chaveAcesso, String numero, String serie, Integer vendaId, LocalDateTime dataEmissao, BigDecimal valorTotal) {
        this.chaveAcesso = chaveAcesso;
        this.numero = numero;
        this.serie = serie;
        this.vendaId = vendaId;
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

    public Integer getVendaId() {
        return vendaId;
    }

    public void setVendaId(Integer vendaId) {
        this.vendaId = vendaId;
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
        if (!super.equals(o)) return false;
        NotaFiscal nf = (NotaFiscal) o;
        return Objects.equals(chaveAcesso, nf.chaveAcesso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), chaveAcesso);
    }

    @Override
    public String toString() {
        return String.format("{ id=%s, chaveAcesso=%s, numero=%s, serie=%s, vendaId=%s, dataEmissao=%s, valorTotal=%s, createdAt=%s, updatedAt=%s }", this.getId(), this.chaveAcesso, this.numero, this.serie, this.vendaId, this.dataEmissao, this.valorTotal, this.getCreatedAt(), this.getUpdatedAt());
    }
}
