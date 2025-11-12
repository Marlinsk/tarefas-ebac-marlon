package br.com.ebac.memelandia.categoria.domain.entities;

import java.time.LocalDate;
import java.util.Objects;

public class CategoriaMeme {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataCadastro;
    private Long usuarioId;

    public CategoriaMeme() {}

    public CategoriaMeme(Long id, String nome, String descricao, LocalDate dataCadastro, Long usuarioId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCadastro = dataCadastro;
        this.usuarioId = usuarioId;
    }

    public static CategoriaMeme criar(String nome, String descricao, Long usuarioId) {
        validarNome(nome);
        validarDescricao(descricao);
        validarUsuarioId(usuarioId);
        return new CategoriaMeme(null, nome, descricao, LocalDate.now(), usuarioId);
    }

    private static void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (nome.length() < 3) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 3 caracteres");
        }
        if (nome.length() > 100) {
            throw new IllegalArgumentException("Nome não pode ter mais de 100 caracteres");
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

    private static void validarUsuarioId(Long usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário é obrigatório e deve ser maior que 0");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaMeme that = (CategoriaMeme) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CategoriaMeme{" + "id=" + id + ", nome='" + nome + '\'' + ", descricao='" + descricao + '\'' + ", dataCadastro=" + dataCadastro + ", usuarioId=" + usuarioId + '}';
    }
}
