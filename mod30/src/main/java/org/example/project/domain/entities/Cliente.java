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

    @TableColumn(dbName = "tel", setJavaName = "setTel")
    private String tel;

    @TableColumn(dbName = "endereco", setJavaName = "setEnd")
    private String end;

    @TableColumn(dbName = "numero", setJavaName = "setNumero")
    private Integer numero;

    @TableColumn(dbName = "cidade", setJavaName = "setCidade")
    private String cidade;

    @TableColumn(dbName = "estado", setJavaName = "setEstado")
    private String estado;

    // Novos campos
    @TableColumn(dbName = "bairro", setJavaName = "setBairro")
    private String bairro;

    @TableColumn(dbName = "cep", setJavaName = "setCep")
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
        if (!super.equals(o)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf) && Objects.equals(email, cliente.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cpf, email);
    }

    public String toString() {
        return String.format("{ id=%s, nome=%s, cpf=%s, email=%s, tel=%s, end=%s, numero=%s, cidade=%s, estado=%s, bairro=%s, cep=%s, createdAt=%s, updatedAt=%s }", super.getId(), this.nome, this.cpf, this.email, this.tel, this.end, this.numero, this.cidade, this.estado, this.bairro, this.cep, super.getCreatedAt(), super.getUpdatedAt());
    }
}
