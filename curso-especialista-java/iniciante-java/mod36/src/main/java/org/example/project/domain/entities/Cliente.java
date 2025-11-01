package org.example.project.domain.entities;

import jakarta.persistence.*;
import org.example.project.common.annotations.BusinessKey;
import org.example.project.domain.model.BaseEntity;

import java.util.Objects;

@Entity
@Table(name = "Cliente", uniqueConstraints = @UniqueConstraint(name = "UK_CLIENTE_CPF", columnNames = "cpf"), indexes = {@Index(name = "IX_CLIENTE_CPF", columnList = "cpf"), @Index(name = "IX_CLIENTE_EMAIL", columnList = "email")})
public class Cliente extends BaseEntity {

    @Column(name = "nome", length = 120, nullable = false)
    private String nome;

    @BusinessKey("cpf")
    @Column(name = "cpf", length = 14, nullable = false)
    private String cpf;

    @Column(name = "email", length = 120, nullable = false)
    private String email;

    @Column(name = "tel", length = 30)
    private String tel;

    @Column(name = "endereco", length = 160)
    private String end;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "cidade", length = 80)
    private String cidade;

    @Column(name = "estado", length = 2)
    private String estado;

    @Column(name = "bairro", length = 80)
    private String bairro;

    @Column(name = "cep", length = 9)
    private String cep;

    public Cliente() {}

    public Cliente(String nome, String cpf, String email, String tel, String end, Integer numero, String cidade, String estado, String bairro, String cep) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.tel = tel;
        this.end = end;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        this.bairro = bairro;
        this.cep = cep;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf) && Objects.equals(email, cliente.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, email);
    }

    @Override
    public String toString() {
        return String.format("{ id=%s, nome=%s, cpf=%s, email=%s, tel=%s, end=%s, numero=%s, cidade=%s, estado=%s, bairro=%s, cep=%s, createdAt=%s, updatedAt=%s }", this.getId(), this.nome, this.cpf, this.email, this.tel, this.end, this.numero, this.cidade, this.estado, this.bairro, this.cep, this.getCreatedAt(), this.getUpdatedAt());
    }
}
