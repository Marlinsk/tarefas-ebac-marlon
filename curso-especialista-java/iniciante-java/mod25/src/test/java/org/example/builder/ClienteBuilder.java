package org.example.builder;

import org.example.application.domain.Cliente;

public class ClienteBuilder {
    private Long cpf = 11111111111L;
    private String nome = "Ana";
    private String email = "ana@email.com";
    private String telefone = "9999-0000";

    public static ClienteBuilder umCliente() {
        return new ClienteBuilder();
    }

    public ClienteBuilder comCpf(Long cpf) {
        this.cpf = cpf;
        return this;
    }

    public ClienteBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ClienteBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public ClienteBuilder comTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public Cliente build() {
        return new Cliente(cpf, nome, email, telefone);
    }
}
