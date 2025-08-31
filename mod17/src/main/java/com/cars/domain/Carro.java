package main.java.com.cars.domain;

public abstract class Carro {
    private TipoCarro tipo;
    private CategoriaCarro categoria;

    public Carro(TipoCarro tipo, CategoriaCarro categoria) {
        this.tipo = tipo;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return String.format(", tipo=%s, categoria=%s", this.tipo, this.categoria);
    }
}
