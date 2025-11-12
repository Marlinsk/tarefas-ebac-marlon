package br.com.ebac.memelandia.categoria.application.dto;

import br.com.ebac.memelandia.categoria.domain.entities.CategoriaMeme;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class CategoriaMemeDTO {
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 5, message = "Descrição deve ter pelo menos 5 caracteres")
    private String descricao;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCadastro;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;

    public CategoriaMemeDTO() {
    }

    public CategoriaMemeDTO(Long id, String nome, String descricao, LocalDate dataCadastro, Long usuarioId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCadastro = dataCadastro;
        this.usuarioId = usuarioId;
    }

    public static CategoriaMemeDTO fromEntity(CategoriaMeme categoriaMeme) {
        return new CategoriaMemeDTO(categoriaMeme.getId(), categoriaMeme.getNome(), categoriaMeme.getDescricao(), categoriaMeme.getDataCadastro(), categoriaMeme.getUsuarioId());
    }

    public CategoriaMeme toEntity() {
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
