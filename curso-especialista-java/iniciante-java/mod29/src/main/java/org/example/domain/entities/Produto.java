package org.example.domain.entities;

import org.example.domain.model.BaseEntity;

import java.math.BigDecimal;

public class Produto extends BaseEntity {
    private String nome;
    private BigDecimal valor;

    public Produto(String nome, BigDecimal valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return String.format("{ id=%s, nome=%s, valor=%s, createdAt=%s, updatedAt=%s }", super.getId(), this.nome, this.valor, super.getCreatedAt(), super.getUpdatedAt());
    }
}
