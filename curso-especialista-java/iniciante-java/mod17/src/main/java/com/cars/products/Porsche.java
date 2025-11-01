package main.java.com.cars.products;

import main.java.com.cars.domain.Carro;
import main.java.com.cars.domain.CategoriaCarro;
import main.java.com.cars.domain.TipoCarro;

public class Porsche extends Carro {
    private final String fabricante;
    private String modelo;

    public Porsche(String modelo, TipoCarro tipo, CategoriaCarro categoria) {
        super(tipo, categoria);
        this.fabricante = getClass().getSimpleName();
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return '{' + "fabricante=" + fabricante + ", modelo=" + modelo + super.toString() + '}';
    }
}
