public class Fibonacci {

    public long calcular(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Não é possível calcular Fibonacci para números negativos");
        }

        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        return calcular(n - 1) + calcular(n - 2);
    }

    public void exibirSequencia(int n) {
        System.out.print("Sequência de Fibonacci até F(" + n + "): ");
        for (int i = 0; i <= n; i++) {
            System.out.print(calcular(i));
            if (i < n) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}
