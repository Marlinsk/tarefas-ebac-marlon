package main.java.com.carfactory.domain;

public abstract class Carro {
    private String fabricante;
    private String modelo;
    private String cor;
    private TipoMotor tipo;
    private int potenciaCv;

    public Carro(String fabricante, String modelo, String cor, TipoMotor tipo, int potenciaCv) {
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.cor = cor;
        this.tipo = tipo;
        this.potenciaCv = potenciaCv;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public TipoMotor getTipo() {
        return tipo;
    }

    public void setTipo(TipoMotor tipo) {
        this.tipo = tipo;
    }

    public int getPotenciaCv() {
        return potenciaCv;
    }

    public void setPotenciaCv(int potenciaCv) {
        this.potenciaCv = potenciaCv;
    }

    public String resumo() {
        return String.format("%s %s %s (%s, %d cv)", this.fabricante, this.modelo, this.cor, this.tipo, this.potenciaCv);
    }
}
