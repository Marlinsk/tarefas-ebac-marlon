public class FibonacciTest {

    private Fibonacci fibonacci;

    public void setUp() {
        fibonacci = new Fibonacci();
        System.out.println("Inicializando testes do Fibonacci...");
    }

    public void testCasosBase() {
        System.out.println("\n=== Teste 1: Casos Base ===");

        long resultado0 = fibonacci.calcular(0);
        assert resultado0 == 0 : "Erro: F(0) deveria ser 0, mas foi " + resultado0;
        System.out.println("\u2713 F(0) = " + resultado0 + " (esperado: 0)");

        long resultado1 = fibonacci.calcular(1);
        assert resultado1 == 1 : "Erro: F(1) deveria ser 1, mas foi " + resultado1;
        System.out.println("\u2713 F(1) = " + resultado1 + " (esperado: 1)");

        System.out.println("Teste de casos base passou!");
    }

    public void testPrimeirosNumeros() {
        System.out.println("\n=== Teste 2: Primeiros Números da Sequência ===");

        long resultado2 = fibonacci.calcular(2);
        assert resultado2 == 1 : "Erro: F(2) deveria ser 1, mas foi " + resultado2;
        System.out.println("\u2713 F(2) = " + resultado2 + " (esperado: 1)");

        long resultado3 = fibonacci.calcular(3);
        assert resultado3 == 2 : "Erro: F(3) deveria ser 2, mas foi " + resultado3;
        System.out.println("\u2713 F(3) = " + resultado3 + " (esperado: 2)");

        long resultado4 = fibonacci.calcular(4);
        assert resultado4 == 3 : "Erro: F(4) deveria ser 3, mas foi " + resultado4;
        System.out.println("\u2713 F(4) = " + resultado4 + " (esperado: 3)");

        long resultado5 = fibonacci.calcular(5);
        assert resultado5 == 5 : "Erro: F(5) deveria ser 5, mas foi " + resultado5;
        System.out.println("\u2713 F(5) = " + resultado5 + " (esperado: 5)");

        long resultado6 = fibonacci.calcular(6);
        assert resultado6 == 8 : "Erro: F(6) deveria ser 8, mas foi " + resultado6;
        System.out.println("\u2713 F(6) = " + resultado6 + " (esperado: 8)");

        System.out.println("Teste de primeiros números passou!");
    }

    public void testNumerosMaiores() {
        System.out.println("\n=== Teste 3: Números Maiores ===");

        long resultado10 = fibonacci.calcular(10);
        assert resultado10 == 55 : "Erro: F(10) deveria ser 55, mas foi " + resultado10;
        System.out.println("\u2713 F(10) = " + resultado10 + " (esperado: 55)");

        long resultado15 = fibonacci.calcular(15);
        assert resultado15 == 610 : "Erro: F(15) deveria ser 610, mas foi " + resultado15;
        System.out.println("\u2713 F(15) = " + resultado15 + " (esperado: 610)");

        long resultado20 = fibonacci.calcular(20);
        assert resultado20 == 6765 : "Erro: F(20) deveria ser 6765, mas foi " + resultado20;
        System.out.println("\u2713 F(20) = " + resultado20 + " (esperado: 6765)");

        System.out.println("Teste de números maiores passou!");
    }

    public void testNumeroNegativo() {
        System.out.println("\n=== Teste 4: Número Negativo ===");

        try {
            fibonacci.calcular(-1);
            assert false : "Erro: Deveria lançar IllegalArgumentException para número negativo";
        } catch (IllegalArgumentException e) {
            System.out.println("\u2713 Exceção lançada corretamente: " + e.getMessage());
        }

        try {
            fibonacci.calcular(-5);
            assert false : "Erro: Deveria lançar IllegalArgumentException para número negativo";
        } catch (IllegalArgumentException e) {
            System.out.println("\u2713 Exceção lançada corretamente para F(-5)");
        }

        System.out.println("Teste de número negativo passou!");
    }

    public void testConsistenciaSequencia() {
        System.out.println("\n=== Teste 5: Consistência da Sequência ===");
        System.out.println("Verificando se F(n) = F(n-1) + F(n-2) para vários valores...");

        for (int n = 2; n <= 12; n++) {
            long fn = fibonacci.calcular(n);
            long fn1 = fibonacci.calcular(n - 1);
            long fn2 = fibonacci.calcular(n - 2);

            assert fn == fn1 + fn2 : "Erro: F(" + n + ") não é igual a F(" + (n-1) + ") + F(" + (n-2) + ")";
            System.out.println("\u2713 F(" + n + ") = " + fn + " = F(" + (n-1) + ") + F(" + (n-2) + ") = " + fn1 + " + " + fn2);
        }

        System.out.println("Teste de consistência passou!");
    }

    public void testExibirSequencia() {
        System.out.println("\n=== Teste 6: Exibir Sequência ===");
        fibonacci.exibirSequencia(10);
        System.out.println("Teste de exibição passou!");
    }

    public void runAllTests() {
        setUp();

        try {
            testCasosBase();
            testPrimeirosNumeros();
            testNumerosMaiores();
            testNumeroNegativo();
            testConsistenciaSequencia();
            testExibirSequencia();

            System.out.println("\n" + "=".repeat(50));
            System.out.println("TODOS OS TESTES FIBONACCI PASSARAM COM SUCESSO!");
            System.out.println("=".repeat(50));
        } catch (AssertionError e) {
            System.err.println("\n" + "=".repeat(50));
            System.err.println("FALHA NO TESTE: " + e.getMessage());
            System.err.println("=".repeat(50));
            throw e;
        }
    }

    public static void main(String[] args) {
        FibonacciTest test = new FibonacciTest();
        test.runAllTests();
    }
}
