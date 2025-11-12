package br.com.ebac.memelandia.memes.domain.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Meme {
    private Long id;
    private String nome;
    private String descricao;
    private String dataUrl;
    private LocalDate dataCadastro;
    private Long usuarioId;
    private Long categoriaId;

    public Meme() {
    }

    public Meme(Long id, String nome, String descricao, String dataUrl, LocalDate dataCadastro, Long usuarioId, Long categoriaId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataUrl = dataUrl;
        this.dataCadastro = dataCadastro;
        this.usuarioId = usuarioId;
        this.categoriaId = categoriaId;
    }

    public static Meme criar(String nome, String descricao, String dataUrl, Long usuarioId, Long categoriaId) {
        validarNome(nome);
        validarDescricao(descricao);
        validarDataUrl(dataUrl);
        validarUsuarioId(usuarioId);
        validarCategoriaId(categoriaId);
        return new Meme(null, nome, descricao, dataUrl, LocalDate.now(), usuarioId, categoriaId);
    }

    private static void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (nome.length() < 3) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 3 caracteres");
        }
    }

    private static void validarDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia");
        }
        if (descricao.length() < 5) {
            throw new IllegalArgumentException("Descrição deve ter pelo menos 5 caracteres");
        }
    }

    private static void validarDataUrl(String dataUrl) {
        if (dataUrl == null || dataUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Data URL não pode ser vazia");
        }
        if (!dataUrl.startsWith("data:image/") && !dataUrl.startsWith("http")) {
            throw new IllegalArgumentException("Data URL inválida");
        }
    }

    private static void validarUsuarioId(Long usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário inválido");
        }
    }

    private static void validarCategoriaId(Long categoriaId) {
        if (categoriaId == null || categoriaId <= 0) {
            throw new IllegalArgumentException("ID da categoria inválido");
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        validarDescricao(descricao);
        this.descricao = descricao;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        validarDataUrl(dataUrl);
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
        validarUsuarioId(usuarioId);
        this.usuarioId = usuarioId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        validarCategoriaId(categoriaId);
        this.categoriaId = categoriaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meme meme = (Meme) o;
        return Objects.equals(id, meme.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Meme{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataUrl='" + dataUrl + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", usuarioId=" + usuarioId +
                ", categoriaId=" + categoriaId +
                '}';
    }
}
