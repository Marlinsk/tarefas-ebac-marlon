package br.com.ebac.memelandia.usuario.infrastructure.persistence;

import br.com.ebac.memelandia.usuario.domain.entity.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    public UsuarioEntity() {}

    public UsuarioEntity(Long id, String nome, String email, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    public static UsuarioEntity fromDomain(Usuario usuario) {
        return new UsuarioEntity(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getDataCadastro());
    }

    public Usuario toDomain() {
        return new Usuario(this.id, this.nome, this.email, this.dataCadastro);
    }

    @PrePersist
    public void prePersist() {
        if (dataCadastro == null) {
            dataCadastro = LocalDate.now();
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
