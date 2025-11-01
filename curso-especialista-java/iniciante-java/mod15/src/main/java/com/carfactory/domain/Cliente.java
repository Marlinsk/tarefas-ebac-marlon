package main.java.com.carfactory.domain;

import main.java.com.carfactory.factories.Fabrica;

import java.time.LocalDate;
import java.util.Objects;

public class Cliente {

    private Fabrica marca;

    public Cliente(Fabrica marca) {
        this.marca = marca;
    }

    public Carro criarPedido(TipoMotor tipo, String modelo, String cor, int potenciaCv) {
        Carro carro = null;

        switch (tipo) {
            case ELETRICO:
                carro = this.marca.criarEletrico(modelo, cor, potenciaCv);
                break;
            case COMBUSTAO:
                carro = this.marca.criarCombustao(modelo, cor, potenciaCv);
                break;
            case HIBRIDO:
                carro = this.marca.criarHibrido(modelo, cor, potenciaCv);
                break;
            default:
                System.out.println(String.format("Não existe esse tipo de carro %s", tipo));
                break;
        }

        if (Objects.isNull(carro)) {
            return null;
        }

        System.out.println(String.format("Pedido de carro modelo %s feito na data %s", carro.resumo(), LocalDate.now()));
        return carro;
    }
}
