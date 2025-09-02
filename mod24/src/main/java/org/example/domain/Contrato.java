package org.example.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Contrato {
    private int numero;
    private LocalDate dataInicio;
    private double valor;

    public Contrato(int numero, LocalDate dataInicio, double valor) {
        this.numero = numero;
        this.dataInicio = dataInicio;
        this.valor = valor;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Contrato)) return false;
        Contrato contrato = (Contrato) o;
        return numero == contrato.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numero);
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "numero=" + numero +
                ", dataInicio=" + dataInicio +
                ", valor=" + valor +
                '}';
    }
}
