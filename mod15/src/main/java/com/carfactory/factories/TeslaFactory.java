package main.java.com.carfactory.factories;

import main.java.com.carfactory.domain.Carro;
import main.java.com.carfactory.domain.TipoMotor;
import main.java.com.carfactory.products.TeslaCar;

public class TeslaFactory implements Fabrica {
    @Override
    public Carro criarEletrico(String modelo, String cor, int potenciaCv) {
        return new TeslaCar(modelo, cor, TipoMotor.ELETRICO, potenciaCv);
    }

    @Override
    public Carro criarCombustao(String modelo, String cor, int potenciaCv) {
        return null;
    }

    @Override
    public Carro criarHibrido(String modelo, String cor, int potenciaCv) {
        return null;
    }
}
