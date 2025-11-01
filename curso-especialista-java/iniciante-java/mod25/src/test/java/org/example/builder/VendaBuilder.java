package org.example.builder;

import org.example.application.domain.Cliente;
import org.example.application.domain.Venda;

public class VendaBuilder {
    private String codigo = "V-001";
    private Cliente cliente = ClienteBuilder.umCliente().build();

    public static VendaBuilder umaVenda() {
        return new VendaBuilder();
    }

    public VendaBuilder comCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public VendaBuilder comCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public Venda build() {
        return new Venda(codigo, cliente);
    }
}
