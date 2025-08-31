package main.java.com.cars;

import main.java.com.cars.domain.Carro;

import java.util.ArrayList;
import java.util.List;

public class ListaCarros implements IListaCarros {
    private final List<Carro> lista = new ArrayList<>();

    @Override
    public void add(Carro obj) {
        lista.add(obj);
    }

    @Override
    public void resume() {
        for (Carro carro : lista) {
            System.out.println(carro.toString());
        }
    }
}
