import java.time.LocalDate;
import java.util.Date;

public class PessoaJuridica extends Pessoa {
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String inscricaoEstadual;
    private LocalDate dataFundacao;

    public PessoaJuridica(String nome, String endereco, String telefone, String email, String cnpj, String razaoSocial, String nomeFantasia, String inscricaoEstadual, LocalDate dataFundacao) {
        super(nome, endereco, telefone, email);
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

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public LocalDate getDataFundacao() {
        return dataFundacao;
    }

    @Override
    public String toString() {
        return "PessoaJuridica{" + super.toString() + "cnpj='" + cnpj + '\'' + ", razaoSocial='" + razaoSocial + '\'' + ", nomeFantasia='" + nomeFantasia + '\'' + ", inscricaoEstadual='" + inscricaoEstadual + '\'' + ", dataFundacao=" + dataFundacao + '}';
    }
}
