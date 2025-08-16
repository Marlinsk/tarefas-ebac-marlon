public abstract class Pessoa {
    private String nome;
    private String endereco;
    private String telefone;
    private String email;

    public Pessoa(String nome, String endereco, String telefone, String email) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "nome='" + nome + '\'' + ", endereco='" + endereco + '\'' + ", telefone='" + telefone + '\'' + ", email='" + email + '\'';
    }
}
