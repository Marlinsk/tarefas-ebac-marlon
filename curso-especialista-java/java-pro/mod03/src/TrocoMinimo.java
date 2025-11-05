import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class TrocoMinimo {

    public static int calcularTrocoMinimo(int quantia, int[] moedas) {
        Arrays.sort(moedas);
        inverterArray(moedas);

        int numeroMoedas = 0;
        int quantiaRestante = quantia;

        System.out.println("Calculando troco para: " + quantia);
        System.out.println("Moedas disponíveis: " + Arrays.toString(moedas));
        System.out.println();

        for (int moeda : moedas) {
            if (quantiaRestante >= moeda) {
                int quantidadeDestaMoeda = quantiaRestante / moeda;
                numeroMoedas += quantidadeDestaMoeda;
                quantiaRestante -= quantidadeDestaMoeda * moeda;

                System.out.println("Usando " + quantidadeDestaMoeda + " moeda(s) de " + moeda);
            }

            if (quantiaRestante == 0) {
                break;
            }
        }

        if (quantiaRestante > 0) {
            System.out.println("\nNão foi possível dar o troco exato com as moedas disponíveis!");
            return -1;
        }

        System.out.println("\nTotal de moedas utilizadas: " + numeroMoedas);
        return numeroMoedas;
    }

    public static class ResultadoTroco {
        public int totalMoedas;
        public List<String> detalhamento;

        public ResultadoTroco() {
            this.totalMoedas = 0;
            this.detalhamento = new ArrayList<>();
        }
    }

    public static ResultadoTroco calcularTrocoDetalhado(int quantia, int[] moedas) {
        ResultadoTroco resultado = new ResultadoTroco();

        Arrays.sort(moedas);
        inverterArray(moedas);

        int quantiaRestante = quantia;

        for (int moeda : moedas) {
            if (quantiaRestante >= moeda) {
                int quantidade = quantiaRestante / moeda;
                resultado.totalMoedas += quantidade;
                resultado.detalhamento.add(quantidade + " x " + moeda);
                quantiaRestante -= quantidade * moeda;
            }

            if (quantiaRestante == 0) {
                break;
            }
        }

        if (quantiaRestante > 0) {
            resultado.totalMoedas = -1;
        }

        return resultado;
    }

    private static void inverterArray(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }

    public static void executeAlgoritmo() {
        System.out.println("\n=== ALGORITMO GULOSO ===\n");

        System.out.println("--- Exemplo 1 ---");
        int quantia1 = 18;
        int[] moedas1 = {5, 2, 1};
         calcularTrocoMinimo(quantia1, moedas1);
        System.out.println("\n" + "=".repeat(40) + "\n");

        System.out.println("--- Exemplo 2 ---");
        int quantia2 = 11;
        int[] moedas2 = {5, 2};
        calcularTrocoMinimo(quantia2, moedas2);
        System.out.println("\n" + "=".repeat(40) + "\n");

        System.out.println("--- Exemplo 3 ---");
        int quantia3 = 27;
        int[] moedas3 = {10, 5, 1};
        calcularTrocoMinimo(quantia3, moedas3);
        System.out.println("\n" + "=".repeat(40) + "\n");

        System.out.println("--- Exemplo 4 (Detalhado) ---");
        int quantia4 = 63;
        int[] moedas4 = {25, 10, 5, 1};
        ResultadoTroco resultado4 = calcularTrocoDetalhado(quantia4, moedas4);

        System.out.println("Quantia: " + quantia4);
        System.out.println("Moedas: " + Arrays.toString(moedas4));
        System.out.println("Detalhamento:");

        for (String detalhe : resultado4.detalhamento) {
            System.out.println("  " + detalhe);
        }

        System.out.println("Total: " + resultado4.totalMoedas + " moedas");
        System.out.println();
    }
}