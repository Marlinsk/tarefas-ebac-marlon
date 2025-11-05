import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exec = true;

        while (exec) {
            System.out.println("=== ALGORITMOS ===\n");
            System.out.println("2 - Exemplo de algoritmo backtraking");
            System.out.println("1 - Exemplo de algoritmo guloso ou greedy");
            System.out.println("0 - Sair\n");
            System.out.print("Digite um dos números para visualizar a execução: ");

            int opcao  = scanner.nextInt();

            if (opcao == 1) {
                escolherExemplo(opcao);
            } else if (opcao == 2) {
                escolherExemplo(opcao);
            }

            if (opcao == 0) {
                System.out.println("Saindo do loop...");
                System.out.println("Programa encerrado.");
                scanner.close();
                exec = false;
            }
        }

    }

    private static void escolherExemplo(int exemplo) {
        switch (exemplo) {
            case 1:
                TrocoMinimo.executeAlgoritmo();
                break;
            case 2:
                Subconjuntos.executeAlgoritmo();
                break;
            default:
                System.out.println("Não existe esse exemplo");
                break;
        }
    }
}