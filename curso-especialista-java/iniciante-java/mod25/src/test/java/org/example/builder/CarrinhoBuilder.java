package org.example.builder;

import org.example.application.domain.Carrinho;
import org.example.application.domain.Cliente;

public class CarrinhoBuilder {
    private Cliente cliente = ClienteBuilder.umCliente().build();

    public static CarrinhoBuilder umCarrinho() {
        return new CarrinhoBuilder();
    }

    public CarrinhoBuilder comCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public Carrinho build() {
        Carrinho c = new Carrinho();
        c.setCliente(cliente);
        return c;
    }
}
