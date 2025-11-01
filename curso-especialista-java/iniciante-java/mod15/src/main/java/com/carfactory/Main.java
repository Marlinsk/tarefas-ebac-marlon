package main.java.com.carfactory;

import main.java.com.carfactory.domain.Cliente;
import main.java.com.carfactory.domain.TipoMotor;
import main.java.com.carfactory.factories.*;

public class Main {
    public static void main(String[] args) {
        Cliente cliente = new Cliente(escolherMarca("BMW"));
        cliente.criarPedido(TipoMotor.ELETRICO, "iX2 xDrive30 M Sport", "Preto", 306);
    }

    public static Fabrica escolherMarca(String marca) {
        switch (marca) {
            case "BMW": return new BMWFactory();
            case "Mercedez-Benz": return new MercedesBenzFactory();
            case "Tesla": return new TeslaFactory();
            case "Porsche": return new PorscheFactory();
            case "Volkswagen": return new VolkswagenFactory();
            default:
                System.out.println("Não existe esse fabricante no mercado");
                return null;
        }
    }
}
