package main.java.com.cars;

import main.java.com.cars.domain.CategoriaCarro;
import main.java.com.cars.domain.TipoCarro;
import main.java.com.cars.products.BMW;
import main.java.com.cars.products.Honda;
import main.java.com.cars.products.Porsche;

public class Main {
    public static void main(String args[]) {
        ListaCarros dealership = new ListaCarros();

        Porsche porsche911 = new Porsche("911 Carrera", TipoCarro.CONVERSIVEL, CategoriaCarro.LUXO);
        dealership.add(porsche911);

        Porsche porscheCayenne = new Porsche("Cayenne", TipoCarro.SUV, CategoriaCarro.LUXO);
        dealership.add(porscheCayenne);

        Honda civic = new Honda(TipoCarro.SEDAN, CategoriaCarro.POPULAR, "Civic", 2024, "2.0 Turbo");
        dealership.add(civic);

        Honda fit = new Honda(TipoCarro.HATCH, CategoriaCarro.POPULAR, "Fit", 2020, "1.5 Flex");
        dealership.add(fit);

        BMW bmwX5 = new BMW("X5", "Preto", "3.0 TwinPower", TipoCarro.SUV, CategoriaCarro.LUXO);
        dealership.add(bmwX5);

        BMW bmwM3 = new BMW("M3", "Azul", "3.0 BiTurbo", TipoCarro.SEDAN, CategoriaCarro.ESPORTIVO);
        dealership.add(bmwM3);

        dealership.resume();
    }
}
