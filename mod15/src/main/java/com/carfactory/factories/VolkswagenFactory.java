package main.java.com.carfactory.factories;

import main.java.com.carfactory.domain.Carro;
import main.java.com.carfactory.domain.TipoMotor;
import main.java.com.carfactory.products.VolkswagenCar;

public class VolkswagenFactory implements Fabrica {
    @Override
    public Carro criarEletrico(String modelo, String cor, int potenciaCv) {
        return new VolkswagenCar(modelo, cor, TipoMotor.ELETRICO, potenciaCv);
    }

    @Override
    public Carro criarCombustao(String modelo, String cor, int potenciaCv) {
        return new VolkswagenCar(modelo, cor, TipoMotor.COMBUSTAO, potenciaCv);
    }

    @Override
    public Carro criarHibrido(String modelo, String cor, int potenciaCv) {
        return new VolkswagenCar(modelo, cor, TipoMotor.HIBRIDO, potenciaCv);
    }
}
