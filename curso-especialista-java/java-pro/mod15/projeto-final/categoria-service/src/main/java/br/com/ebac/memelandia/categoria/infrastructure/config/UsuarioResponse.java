package br.com.ebac.memelandia.categoria.infrastructure.config;

import java.time.LocalDate;

public class UsuarioResponse {
    private Long id;
    private String nome;
    private String email;
    private LocalDate dataCadastro;

    public UsuarioResponse() {}

    public UsuarioResponse(Long id, String nome, String email, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
