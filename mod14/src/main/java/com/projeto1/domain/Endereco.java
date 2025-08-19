package main.java.com.projeto1.domain;

public class Endereco {
    private final String logradouro;
    private final String numero;
    private final String bairro;
    private final String cidade;
    private final String uf;
    private final String cep;

    public Endereco(String logradouro, String numero, String bairro, String cidade, String uf, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public String getNumero() {
        return this.numero;
    }

    public String getBairro() {
        return this.bairro;
    }

    public String getCidade() {
        return this.cidade;
    }

    public String getUf() {
        return this.uf;
    }

    public String getCep() {
        return this.cep;
    }

    @Override
    public String toString() {
        return String.format("%s, %s - %s, %s/%s, %s", this.logradouro, this.numero, this.bairro, this.cidade, this.uf, this.cep);
    }
}
