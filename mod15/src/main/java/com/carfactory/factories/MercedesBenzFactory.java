package main.java.com.carfactory.factories;

import main.java.com.carfactory.domain.Carro;
import main.java.com.carfactory.domain.TipoMotor;
import main.java.com.carfactory.products.MercedesBenzCar;

public class MercedesBenzFactory implements Fabrica {
    @Override
    public Carro criarEletrico(String modelo, String cor, int potenciaCv) {
        return new MercedesBenzCar(modelo, cor, TipoMotor.ELETRICO, potenciaCv);
    }

    @Override
    public Carro criarCombustao(String modelo, String cor, int potenciaCv) {
        return new MercedesBenzCar(modelo, cor, TipoMotor.COMBUSTAO, potenciaCv);
    }

    @Override
    public Carro criarHibrido(String modelo, String cor, int potenciaCv) {
        return new MercedesBenzCar(modelo, cor, TipoMotor.HIBRIDO, potenciaCv);
    }
}
