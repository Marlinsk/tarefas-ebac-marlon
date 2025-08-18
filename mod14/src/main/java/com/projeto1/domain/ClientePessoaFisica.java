package main.java.com.projeto1.domain;

import java.time.LocalDate;

public class ClientePessoaFisica extends Cliente {
    private final String cpf;
    private LocalDate dataNascimento;

    public ClientePessoaFisica(String nome, String email, String contato, Endereco endereco, String cpf, LocalDate dataNascimento) {
        super(nome, email, contato, endereco);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String toString() {
        return String.format("%s [PF cpf=%s]", super.toString(), this.cpf);
    }
}
