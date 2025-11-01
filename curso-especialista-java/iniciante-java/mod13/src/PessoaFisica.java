import java.time.LocalDate;

public class PessoaFisica extends Pessoa {
    private String cpf;
    private String rg;
    private LocalDate dataNascimento;
    private String sexo;
    private String estadoCivil;

    public PessoaFisica(String nome, String endereco, String telefone, String email, String cpf, String rg, LocalDate dataNascimento, String sexo, String estadoCivil) {
        super(nome, endereco, telefone, email);
        this.cpf = cpf;
        this.rg = rg;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.estadoCivil = estadoCivil;
    }

    public String getCpf() {
        return cpf;
    }

    public String getRg() {
        return rg;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    @Override
    public String toString() {
        return "PessoaFisica{" + super.toString() + "cpf='" + cpf + '\'' + ", rg='" + rg + '\'' + ", dataNascimento=" + dataNascimento + ", sexo='" + sexo + '\'' + ", estadoCivil='" + estadoCivil + '\'' + '}';
    }
}
