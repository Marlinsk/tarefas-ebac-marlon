package org.example.domain.entities;

import org.example.domain.model.BaseEntity;

import java.util.Objects;

public class Cliente extends BaseEntity {
    private String nome;
    private String email;

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(email, cliente.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return String.format("{ id=%s, nome=%s, email=%s, createdAt=%s, updatedAt=%s }", super.getId(), this.nome, this.email, super.getCreatedAt(), super.getUpdatedAt());
    }
}
