package org.example.application.domain;

import org.example.application.annotation.TipoChave;
import org.example.application.dao.interfaces.Persistente;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Nota implements Persistente {

    @TipoChave("getCodigo")
    private String codigo;
    private String serie;
    private String chaveAcesso;
    private LocalDateTime dataEmissao;

    private BigDecimal valorTotal;

    private Venda venda;

    public Nota(String codigo, String serie, String chaveAcesso, LocalDateTime dataEmissao, BigDecimal valorTotal, Venda venda) {
        this.codigo = codigo;
        this.serie = serie;
        this.chaveAcesso = chaveAcesso;
        this.dataEmissao = dataEmissao;
        this.valorTotal = valorTotal;
        this.venda = venda;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
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

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Nota)) return false;
        Nota nota = (Nota) o;
        return Objects.equals(codigo, nota.codigo) && Objects.equals(serie, nota.serie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, serie);
    }

    @Override
    public String toString() {
        return "Nota{" + "codigo='" + codigo + '\'' + ", serie='" + serie + '\'' + ", dataEmissao=" + dataEmissao + ", valorTotal=" + valorTotal + '}';
    }
}
