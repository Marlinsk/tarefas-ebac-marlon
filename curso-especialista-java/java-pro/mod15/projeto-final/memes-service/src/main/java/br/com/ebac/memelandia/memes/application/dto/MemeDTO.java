package br.com.ebac.memelandia.memes.application.dto;

import br.com.ebac.memelandia.memes.domain.entity.Meme;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class MemeDTO {
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, message = "Nome deve ter pelo menos 3 caracteres")
    private String nome;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 5, message = "Descrição deve ter pelo menos 5 caracteres")
    private String descricao;

    @NotBlank(message = "Data URL é obrigatória")
    private String dataUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCadastro;

    @NotNull(message = "ID do usuário é obrigatório")
    @Positive(message = "ID do usuário deve ser positivo")
    private Long usuarioId;

    @NotNull(message = "ID da categoria é obrigatório")
    @Positive(message = "ID da categoria deve ser positivo")
    private Long categoriaId;

    public MemeDTO() {
    }

    public MemeDTO(Long id, String nome, String descricao, String dataUrl, LocalDate dataCadastro, Long usuarioId, Long categoriaId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataUrl = dataUrl;
        this.dataCadastro = dataCadastro;
        this.usuarioId = usuarioId;
        this.categoriaId = categoriaId;
    }

    public static MemeDTO fromEntity(Meme meme) {
        return new MemeDTO(meme.getId(), meme.getNome(), meme.getDescricao(), meme.getDataUrl(), meme.getDataCadastro(), meme.getUsuarioId(), meme.getCategoriaId());
    }

    public Meme toEntity() {
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
