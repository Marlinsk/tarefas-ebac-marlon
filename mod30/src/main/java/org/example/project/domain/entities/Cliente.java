package org.example.project.domain.entities;

import org.example.project.common.annotations.BusinessKey;
import org.example.project.common.annotations.TableColumn;
import org.example.project.common.annotations.TableName;
import org.example.project.domain.model.BaseEntity;

import java.util.Objects;

@TableName("Cliente")
public class Cliente extends BaseEntity {

    @TableColumn(dbName = "nome", setJavaName = "setNome")
    private String nome;

    @BusinessKey(value = "cpf")
    @TableColumn(dbName = "cpf", setJavaName = "setCpf")
    private String cpf;

    @TableColumn(dbName = "email", setJavaName = "setEmail")
    private String email;

    public Cliente() {}

    public Cliente(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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
        if (!super.equals(o)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf) && Objects.equals(email, cliente.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cpf, email);
    }

    @Override
    public String toString() {
        return String.format("{ id=%s, nome=%s, cpf=%s, email=%s, createdAt=%s, updatedAt=%s }", super.getId(), this.nome, this.cpf, this.email, super.getCreatedAt(), super.getUpdatedAt());
    }
}
