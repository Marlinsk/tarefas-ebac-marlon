package br.com.ebac.animal_service.domain.entity;

import br.com.ebac.animal_service.domain.enums.Porte;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "animais")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeProvisorio;

    @Column(nullable = false)
    private Integer idadeEstimada;

    @Column(nullable = false)
    private String racaOuEspecie;

    @Column(nullable = false)
    private LocalDate dataEntrada;

    @Column(nullable = true)
    private LocalDate dataAdocao;

    @Column(length = 1000)
    private String descricaoCondicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionarioRecebedor;

    @Column(nullable = true)
    private LocalDate dataObito;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Porte porte;

    public Animal() {}

    public Animal(String nomeProvisorio, Integer idadeEstimada, String racaOuEspecie, LocalDate dataEntrada, String descricaoCondicao, Funcionario funcionarioRecebedor, Porte porte) {
        this.nomeProvisorio = nomeProvisorio;
        this.idadeEstimada = idadeEstimada;
        this.racaOuEspecie = racaOuEspecie;
        this.dataEntrada = dataEntrada;
        this.descricaoCondicao = descricaoCondicao;
        this.funcionarioRecebedor = funcionarioRecebedor;
        this.porte = porte;
    }

    public boolean isAdotado() {
        return dataAdocao != null && dataObito == null;
    }

    public boolean isDisponivel() {
        return dataAdocao == null && dataObito == null;
    }

    public void adotar(LocalDate dataAdocao) {
        if (this.dataObito != null) {
            throw new IllegalStateException("Não é possível adotar um animal que já faleceu");
        }

        if (this.dataAdocao != null) {
            throw new IllegalStateException("Animal já foi adotado");
        }
        this.dataAdocao = dataAdocao;
    }

    public void registrarObito(LocalDate dataObito) {
        if (this.dataObito != null) {
            throw new IllegalStateException("Óbito já registrado");
        }
        this.dataObito = dataObito;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProvisorio() {
        return nomeProvisorio;
    }

    public void setNomeProvisorio(String nomeProvisorio) {
        this.nomeProvisorio = nomeProvisorio;
    }

    public Integer getIdadeEstimada() {
        return idadeEstimada;
    }

    public void setIdadeEstimada(Integer idadeEstimada) {
        this.idadeEstimada = idadeEstimada;
    }

    public String getRacaOuEspecie() {
        return racaOuEspecie;
    }

    public void setRacaOuEspecie(String racaOuEspecie) {
        this.racaOuEspecie = racaOuEspecie;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataAdocao() {
        return dataAdocao;
    }

    public void setDataAdocao(LocalDate dataAdocao) {
        this.dataAdocao = dataAdocao;
    }

    public String getDescricaoCondicao() {
        return descricaoCondicao;
    }

    public void setDescricaoCondicao(String descricaoCondicao) {
        this.descricaoCondicao = descricaoCondicao;
    }

    public Funcionario getFuncionarioRecebedor() {
        return funcionarioRecebedor;
    }

    public void setFuncionarioRecebedor(Funcionario funcionarioRecebedor) {
        this.funcionarioRecebedor = funcionarioRecebedor;
    }

    public LocalDate getDataObito() {
        return dataObito;
    }

    public void setDataObito(LocalDate dataObito) {
        this.dataObito = dataObito;
    }

    public Porte getPorte() {
        return porte;
    }

    public void setPorte(Porte porte) {
        this.porte = porte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Animal{" + "id=" + id + ", nomeProvisorio='" + nomeProvisorio + '\'' + ", racaOuEspecie='" + racaOuEspecie + '\'' + ", porte=" + porte + '}';
    }
}
