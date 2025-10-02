package org.example.project.domain.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "TB_ACESSORIO", uniqueConstraints = {@UniqueConstraint(name = "UK_ACESSORIO_NOME", columnNames = "NOME")})
@SequenceGenerator(name = "seq_acessorio", sequenceName = "SQ_ACESSORIO", allocationSize = 1, initialValue = 1)
public class Acessorio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_acessorio")
    private Long id;

    @Column(name = "NOME", length = 80, nullable = false)
    private String nome;

    @Column(name = "PRECO", precision = 15, scale = 2, nullable = false)
    private BigDecimal preco;

    @ManyToMany(mappedBy = "acessorios")
    private Set<Carro> carros = new LinkedHashSet<>();

    @OneToOne(mappedBy = "acessorioPrincipal")
    private Marca marcaQueUsaComoPrincipal;

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

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Set<Carro> getCarros() {
        return carros;
    }

    public void setCarros(Set<Carro> carros) {
        this.carros = carros;
    }

    public Marca getMarcaQueUsaComoPrincipal() {
        return marcaQueUsaComoPrincipal;
    }

    public void setMarcaQueUsaComoPrincipal(Marca marcaQueUsaComoPrincipal) {
        this.marcaQueUsaComoPrincipal = marcaQueUsaComoPrincipal;
    }
}
