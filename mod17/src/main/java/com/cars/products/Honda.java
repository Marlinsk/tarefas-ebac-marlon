package main.java.com.cars.products;

import main.java.com.cars.domain.Carro;
import main.java.com.cars.domain.CategoriaCarro;
import main.java.com.cars.domain.TipoCarro;

public class Honda extends Carro {
    private final String fabricante;
    private String modelo;
    private Integer ano;
    private String motor;

    public Honda(TipoCarro tipo, CategoriaCarro categoria, String modelo, Integer ano, String motor) {
        super(tipo, categoria);
        this.fabricante = getClass().getSimpleName();
        this.modelo = modelo;
        this.ano = ano;
        this.motor = motor;
    }

    @Override
    public String toString() {
        return '{' + "fabricante=" + fabricante + ", modelo=" + modelo + ", ano=" + ano + ", motor=" + motor + super.toString() + '}';
    }
}
