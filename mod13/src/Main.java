import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        PessoaJuridica empresaXTO = new PessoaJuridica("Empresa XPTO", "Av. Paulista, 1000", "(11) 4000-1234", "contato@xptotech.com", "12.345.678/0001-99", "XPTO Soluções em Tecnologia Ltda", "XPTO Tech", "123.456.789.000", LocalDate.of(1990, 5, 20));
        PessoaFisica pessoa = new PessoaFisica("Hanz von Sepmayer", "Rua das Flores, 122", "(11) 99999-9999", "sep.mmayer@email.com", "123.456.789-00", "12.345.678-9", LocalDate.of(2010, 3, 15), "Masculino", "Solteiro");
        System.out.println(pessoa);
        System.out.println(empresaXTO);
    }
}