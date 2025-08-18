package main.java.com.projeto1.domain;

import java.time.LocalDate;

public class ClientePessoaJuridica extends Cliente {
    private final String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String inscricaoEstadual;
    private LocalDate dataFundacao;

    public ClientePessoaJuridica(String nome, String email, String contato, Endereco endereco, String cnpj, String razaoSocial, String nomeFantasia, String inscricaoEstadual, LocalDate dataFundacao) {
        super(nome, email, contato, endereco);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.inscricaoEstadual = inscricaoEstadual;
        this.dataFundacao = dataFundacao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public LocalDate getDataFundacao() {
        return dataFundacao;
    }

    public void setDataFundacao(LocalDate dataFundacao) {
        this.dataFundacao = dataFundacao;
    }

    public String toString() {
        return String.format("%s [PJ cnpj=%s, razao=%s]", super.toString(), this.cnpj, this.razaoSocial);
    }
}
