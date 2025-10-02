package org.example.project.domain.entities;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "TB_MARCA", uniqueConstraints = {@UniqueConstraint(name = "UK_MARCA_NOME", columnNames = "NOME")})
@SequenceGenerator(name = "seq_marca", sequenceName = "SQ_MARCA", allocationSize = 1, initialValue = 1)
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_marca")
    private Long id;

    @Column(name = "NOME", length = 80, nullable = false)
    private String nome;

    @Column(name = "PAIS_ORIGEM", length = 60)
    private String paisOrigem;

    @OneToMany(mappedBy = "marca", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
    private Set<Carro> carros = new LinkedHashSet<>();

    @OneToOne
    @JoinColumn(name = "ACESSORIO_PRINCIPAL_ID", unique = true, foreignKey = @ForeignKey(name = "FK_MARCA_ACESSORIO_PRINCIPAL"))
    private Acessorio acessorioPrincipal;

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

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }

    public Set<Carro> getCarros() {
        return carros;
    }

    public void setCarros(Set<Carro> carros) {
        this.carros = carros;
    }

    public Acessorio getAcessorioPrincipal() {
        return acessorioPrincipal;
    }

    public void setAcessorioPrincipal(Acessorio acessorioPrincipal) {
        this.acessorioPrincipal = acessorioPrincipal;
    }

    public void addCarro(Carro c) {
        carros.add(c);
        c.setMarca(this);
    }

    public void removeCarro(Carro c) {
        carros.remove(c);
        c.setMarca(null);
    }
}
