package main.java.com.carfactory.factories;

import main.java.com.carfactory.domain.Carro;

public interface Fabrica {
    Carro criarEletrico(String modelo, String cor, int potenciaCv);
    Carro criarCombustao(String modelo, String cor, int potenciaCv);
    Carro criarHibrido(String modelo, String cor, int potenciaCv);
}
