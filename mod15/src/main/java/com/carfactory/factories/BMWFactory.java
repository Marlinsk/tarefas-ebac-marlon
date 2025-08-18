package main.java.com.carfactory.factories;

import main.java.com.carfactory.domain.Carro;
import main.java.com.carfactory.domain.TipoMotor;
import main.java.com.carfactory.products.BMWCar;

public class BMWFactory implements Fabrica {
    @Override
    public Carro criarEletrico(String modelo, String cor, int potenciaCv) {
        return new BMWCar(modelo, cor, TipoMotor.ELETRICO, potenciaCv);
    }

    @Override
    public Carro criarCombustao(String modelo, String cor, int potenciaCv) {
        return new BMWCar(modelo, cor, TipoMotor.COMBUSTAO, potenciaCv);
    }

    @Override
    public Carro criarHibrido(String modelo, String cor, int potenciaCv) {
        return new BMWCar(modelo, cor, TipoMotor.COMBUSTAO, potenciaCv);
    }
}
