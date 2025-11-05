import java.util.ArrayList;
import java.util.List;

public class Subconjuntos {

    public static List<List<Integer>> encontrarSubconjuntos(int[] S, int n) {
        List<List<Integer>> resultado = new ArrayList<>();
        List<Integer> subconjuntoAtual = new ArrayList<>();

        if (n < 0 || n > S.length) {
            return resultado;
        }

        backtrack(S, n, 0, subconjuntoAtual, resultado);
        return resultado;
    }

    private static void backtrack(int[] S, int n, int inicio, List<Integer> subconjuntoAtual, List<List<Integer>> resultado) {
        if (subconjuntoAtual.size() == n) {
            resultado.add(new ArrayList<>(subconjuntoAtual));
            return;
        }

        for (int i = inicio; i < S.length; i++) {
            int elementosRestantes = S.length - i;
            int elementosNecessarios = n - subconjuntoAtual.size();

            if (elementosRestantes < elementosNecessarios) {
                break;
            }

            subconjuntoAtual.add(S[i]);
            backtrack(S, n, i + 1, subconjuntoAtual, resultado);
            subconjuntoAtual.remove(subconjuntoAtual.size() - 1);
        }
    }

    private static void imprimirResultado(List<List<Integer>> subconjuntos) {
        System.out.print("[");
        for (int i = 0; i < subconjuntos.size(); i++) {
            System.out.print(subconjuntos.get(i));
            if (i < subconjuntos.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    public static void executeAlgoritmo() {
        System.out.println("\n=== ALGORITMO BACKTRACKING ===\n");

        System.out.println("Exemplo 1:");
        System.out.println("Entrada: S = [1,2,3], n = 2");

        int[] S1 = {1, 2, 3};
        List<List<Integer>> resultado1 = encontrarSubconjuntos(S1, 2);
        System.out.print("Saída: ");
        imprimirResultado(resultado1);

        System.out.println("\n" + "=".repeat(50) + "\n");

        System.out.println("Exemplo 2:");
        System.out.println("Entrada: S = [1,2,3,4], n = 1");
        int[] S2 = {1, 2, 3, 4};
        List<List<Integer>> resultado2 = encontrarSubconjuntos(S2, 1);
        System.out.print("Saída: ");
        imprimirResultado(resultado2);

        System.out.println("\n" + "=".repeat(50) + "\n");

        System.out.println("Exemplo 3:");
        System.out.println("Entrada: S = [1,2,3,4], n = 3");
        int[] S3 = {1, 2, 3, 4};
        List<List<Integer>> resultado3 = encontrarSubconjuntos(S3, 3);
        System.out.print("Saída: ");
        imprimirResultado(resultado3);

        System.out.println("\n" + "=".repeat(50) + "\n");

        System.out.println("Exemplo 4 (caso extremo):");
        System.out.println("Entrada: S = [1,2,3], n = 0");
        int[] S4 = {1, 2, 3};
        List<List<Integer>> resultado4 = encontrarSubconjuntos(S4, 0);
        System.out.print("Saída: ");
        imprimirResultado(resultado4);

        System.out.println("\n" + "=".repeat(50) + "\n");

        System.out.println("Exemplo 5 (caso extremo):");
        System.out.println("Entrada: S = [1,2,3], n = 3");
        int[] S5 = {1, 2, 3};
        List<List<Integer>> resultado5 = encontrarSubconjuntos(S5, 3);
        System.out.print("Saída: ");
        imprimirResultado(resultado5);
        System.out.println();
    }
}
