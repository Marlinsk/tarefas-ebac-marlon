package org.example.application.domain;

import org.example.application.annotation.TipoChave;
import org.example.application.dao.interfaces.Persistente;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Produto implements Persistente {

    @TipoChave("getCodigo")
    private String codigo;
    private String nome;
    private String descricao;
    private BigDecimal valor;

    public Produto(String codigo, String nome, BigDecimal valor) {
        this.codigo = trimOrNull(codigo);
        this.nome = trimOrNull(nome);
        this.valor = normalizeMoney(valor);
    }

    public Produto(String codigo, String nome, String descricao, BigDecimal valor) {
        this.codigo = trimOrNull(codigo);
        this.nome = trimOrNull(nome);
        this.descricao = trimOrNull(descricao);
        this.valor = normalizeMoney(valor);
        validar();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void validar() {
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalStateException("Código é obrigatório");
        }
        if (nome == null || nome.isEmpty()) {
            throw new IllegalStateException("Nome é obrigatório");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Valor não pode ser negativo ou nulo");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto produto = (Produto) o;
        return Objects.equals(codigo, produto.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Produto{" + "codigo='" + codigo + '\'' + ", nome='" + nome + '\'' + ", valor=" + valor + (descricao != null ? ", descricao='" + descricao + '\'' : "") + '}';
    }

    private static String trimOrNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private static String nullSafe(String s) {
        return s == null ? "" : s;
    }

    private static BigDecimal normalizeMoney(BigDecimal v) {
        if (v == null) return null;
        return v.setScale(2, RoundingMode.HALF_UP);
    }
}
