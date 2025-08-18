package main.java.com.carfactory.products;

import main.java.com.carfactory.domain.Carro;
import main.java.com.carfactory.domain.TipoMotor;

public class VolkswagenCar extends Carro {
    public VolkswagenCar(String modelo, String cor, TipoMotor tipo, int potenciaCv) {
        super("BMW", modelo, cor, tipo, potenciaCv);
    }
}
