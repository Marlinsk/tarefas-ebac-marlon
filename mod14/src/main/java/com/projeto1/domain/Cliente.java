package main.java.com.projeto1.domain;

public abstract class Cliente extends Entidade {
    private String nome;
    private String email;
    private String contato;
    private Endereco endereco;

    public Cliente(String nome, String email, String contato, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.contato = contato;
        this.endereco = endereco;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContato() {
        return this.contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return String.format("%s nome=%s, email=%s, contato=%s", super.toString(), this.nome, this.email, this.contato);
    }
}
