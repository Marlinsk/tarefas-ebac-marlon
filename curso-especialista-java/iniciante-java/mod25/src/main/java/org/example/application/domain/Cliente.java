package org.example.application.domain;

import org.example.application.annotation.TipoChave;
import org.example.application.dao.interfaces.Persistente;

import java.util.Objects;

public class Cliente implements Persistente {

    @TipoChave("getCpf")
    private Long cpf;
    private String nome;
    private String email;
    private String telefone;

    public Cliente(Long cpf, String nome, String email, String telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente that = (Cliente) o;
        return Objects.equals(cpf, that.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "Cliente{" + "cpf=" + cpf + ", nome='" + nome + '\'' + ", email='" + email + '\'' + ", telefone='" + telefone + '\'' + '}';
    }
}
