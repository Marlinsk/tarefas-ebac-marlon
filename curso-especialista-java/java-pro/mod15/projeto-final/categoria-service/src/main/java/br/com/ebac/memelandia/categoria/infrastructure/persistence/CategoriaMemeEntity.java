package br.com.ebac.memelandia.categoria.infrastructure.persistence;

import br.com.ebac.memelandia.categoria.domain.entities.CategoriaMeme;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "categoria_meme")
public class CategoriaMemeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    public CategoriaMemeEntity() {
    }

    public CategoriaMemeEntity(Long id, String nome, String descricao, LocalDate dataCadastro, Long usuarioId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCadastro = dataCadastro;
        this.usuarioId = usuarioId;
    }

    public static CategoriaMemeEntity fromDomain(CategoriaMeme categoriaMeme) {
        return new CategoriaMemeEntity(categoriaMeme.getId(), categoriaMeme.getNome(), categoriaMeme.getDescricao(), categoriaMeme.getDataCadastro(), categoriaMeme.getUsuarioId());
    }

    public CategoriaMeme toDomain() {
        return new CategoriaMeme(this.id, this.nome, this.descricao, this.dataCadastro, this.usuarioId);
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
