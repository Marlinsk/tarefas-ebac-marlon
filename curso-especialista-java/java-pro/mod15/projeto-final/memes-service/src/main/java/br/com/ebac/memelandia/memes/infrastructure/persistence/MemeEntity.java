package br.com.ebac.memelandia.memes.infrastructure.persistence;

import br.com.ebac.memelandia.memes.domain.entity.Meme;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "meme")
public class MemeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "data_url", nullable = false, columnDefinition = "LONG VARCHAR")
    private String dataUrl;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "categoria_id", nullable = false)
    private Long categoriaId;

    public MemeEntity() {
    }

    public MemeEntity(Long id, String nome, String descricao, String dataUrl, LocalDate dataCadastro, Long usuarioId, Long categoriaId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataUrl = dataUrl;
        this.dataCadastro = dataCadastro;
        this.usuarioId = usuarioId;
        this.categoriaId = categoriaId;
    }

    public static MemeEntity fromDomain(Meme meme) {
        return new MemeEntity(
                meme.getId(),
                meme.getNome(),
                meme.getDescricao(),
                meme.getDataUrl(),
                meme.getDataCadastro(),
                meme.getUsuarioId(),
                meme.getCategoriaId()
        );
    }

    public Meme toDomain() {
        return new Meme(this.id, this.nome, this.descricao, this.dataUrl, this.dataCadastro, this.usuarioId, this.categoriaId);
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

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
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

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
