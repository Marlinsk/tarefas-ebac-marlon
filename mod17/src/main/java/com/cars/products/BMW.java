package main.java.com.cars.products;

import main.java.com.cars.domain.Carro;
import main.java.com.cars.domain.CategoriaCarro;
import main.java.com.cars.domain.TipoCarro;

public class BMW extends Carro {
    private final String fabricante;
    private String modelo;
    private String cor;
    private String motor;

    public BMW(String modelo, String cor, String motor, TipoCarro tipo, CategoriaCarro categoria) {
        super(tipo, categoria);
        this.fabricante = getClass().getSimpleName();
        this.modelo = modelo;
        this.cor = cor;
        this.motor = motor;
    }

    @Override
    public String toString() {
        return '{' + "fabricante=" + fabricante + ", modelo=" + modelo + ", cor=" + cor + ", motor=" + motor + super.toString() + '}';
    }
}
