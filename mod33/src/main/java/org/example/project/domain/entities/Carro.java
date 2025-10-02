package org.example.project.domain.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "TB_CARRO", indexes = { @Index(name = "IX_CARRO_MODELO", columnList = "MODELO")})
@SequenceGenerator(name = "seq_carro", sequenceName = "SQ_CARRO", allocationSize = 1, initialValue = 1)
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_carro")
    private Long id;

    @Column(name = "MODELO", length = 80, nullable = false)
    private String modelo;

    @Column(name = "ANO_FAB", nullable = false)
    private Integer anoFabricacao;

    @Column(name = "VALOR", precision = 15, scale = 2, nullable = false)
    private BigDecimal valor;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "MARCA_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_CARRO_MARCA"))
    private Marca marca;

    @ManyToMany
    @JoinTable(name = "TB_CARRO_ACESSORIO", joinColumns = @JoinColumn(name = "CARRO_ID", foreignKey = @ForeignKey(name = "FK_CARRO_ACESSORIO_CARRO")), inverseJoinColumns = @JoinColumn(name = "ACESSORIO_ID", foreignKey = @ForeignKey(name = "FK_CARRO_ACESSORIO_ACESSORIO")), uniqueConstraints = { @UniqueConstraint(name = "UK_CARRO_ACESSORIO", columnNames = {"CARRO_ID", "ACESSORIO_ID"})})
    private Set<Acessorio> acessorios = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(Integer anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Set<Acessorio> getAcessorios() {
        return acessorios;
    }

    public void setAcessorios(Set<Acessorio> acessorios) {
        this.acessorios = acessorios;
    }

    public void addAcessorio(Acessorio a) {
        acessorios.add(a);
        a.getCarros().add(this);
    }

    public void removeAcessorio(Acessorio a) {
        acessorios.remove(a);
        a.getCarros().remove(this);
    }
}
