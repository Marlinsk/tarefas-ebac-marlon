package br.com.ebac.memelandia.usuario.domain.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private LocalDate dataCadastro;

    public Usuario() {}

    public Usuario(Long id, String nome, String email, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    public static Usuario criar(String nome, String email) {
        validarNome(nome);
        validarEmail(email);
        return new Usuario(null, nome, email, LocalDate.now());
    }

    private static void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (nome.trim().length() < 3) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 3 caracteres");
        }
        if (nome.trim().length() > 100) {
            throw new IllegalArgumentException("Nome deve ter no máximo 100 caracteres");
        }
    }

    private static void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Email inválido");
        }
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
        validarNome(nome);
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validarEmail(email);
        this.email = email;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nome='" + nome + '\'' + ", email='" + email + '\'' + ", dataCadastro=" + dataCadastro + '}';
    }
}
