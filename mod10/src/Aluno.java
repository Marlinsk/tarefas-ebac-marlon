public class Aluno {
    private final String nome;
    private final double nota1;
    private final double nota2;
    private final double nota3;
    private final double nota4;

    public Aluno(String nome, double nota1, double nota2, double nota3, double nota4) {
        this.nome = nome;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.nota4 = nota4;
    }

    public double calcularMedia() {
        return (nota1 + nota2 + nota3 + nota4) / 4;
    }

    public void exibirResultado() {
        double nota = calcularMedia();
        System.out.printf("A média de notas do " + nome +  " é: %.2f%n", nota);

        if (nota >= 7) {
            System.out.println("Situação: Aprovado!");
        } else if (nota >= 5) {
            System.out.println("Situação: Em recuperação!");
        } else {
            System.out.println("Situação: Reprovado!");
        }
    }

}
