public class CalculadoraTest {

    private Calculadora calculadora;

    public void setUp() {
        calculadora = new Calculadora();
        System.out.println("Inicializando testes da Calculadora...");
    }

    public void testAdicionar() {
        System.out.println("\n=== Teste: Adição ===");

        int resultado1 = calculadora.executarAdicao(5, 3);
        assert resultado1 == 8 : "Erro: 5 + 3 deveria ser 8, mas foi " + resultado1;
        System.out.println("\u2713 5 + 3 = " + resultado1 + " (esperado: 8)");

        int resultado2 = calculadora.executarAdicao(-5, 3);
        assert resultado2 == -2 : "Erro: -5 + 3 deveria ser -2, mas foi " + resultado2;
        System.out.println("\u2713 -5 + 3 = " + resultado2 + " (esperado: -2)");

        int resultado3 = calculadora.executarAdicao(0, 0);
        assert resultado3 == 0 : "Erro: 0 + 0 deveria ser 0, mas foi " + resultado3;
        System.out.println("\u2713 0 + 0 = " + resultado3 + " (esperado: 0)");

        int resultado4 = calculadora.executarAdicao(100, 200);
        assert resultado4 == 300 : "Erro: 100 + 200 deveria ser 300, mas foi " + resultado4;
        System.out.println("\u2713 100 + 200 = " + resultado4 + " (esperado: 300)");

        System.out.println("Todos os testes de adição passaram!");
    }

    public void testSubtrair() {
        System.out.println("\n=== Teste: Subtração ===");

        int resultado1 = calculadora.executarSubtracao(10, 3);
        assert resultado1 == 7 : "Erro: 10 - 3 deveria ser 7, mas foi " + resultado1;
        System.out.println("\u2713 10 - 3 = " + resultado1 + " (esperado: 7)");

        int resultado2 = calculadora.executarSubtracao(5, 10);
        assert resultado2 == -5 : "Erro: 5 - 10 deveria ser -5, mas foi " + resultado2;
        System.out.println("\u2713 5 - 10 = " + resultado2 + " (esperado: -5)");

        int resultado3 = calculadora.executarSubtracao(0, 0);
        assert resultado3 == 0 : "Erro: 0 - 0 deveria ser 0, mas foi " + resultado3;
        System.out.println("\u2713 0 - 0 = " + resultado3 + " (esperado: 0)");

        int resultado4 = calculadora.executarSubtracao(-5, -3);
        assert resultado4 == -2 : "Erro: -5 - (-3) deveria ser -2, mas foi " + resultado4;
        System.out.println("\u2713 -5 - (-3) = " + resultado4 + " (esperado: -2)");

        System.out.println("Todos os testes de subtração passaram!");
    }

    public void testMultiplicar() {
        System.out.println("\n=== Teste: Multiplicação ===");

        int resultado1 = calculadora.executarMultiplicacao(5, 3);
        assert resultado1 == 15 : "Erro: 5 * 3 deveria ser 15, mas foi " + resultado1;
        System.out.println("\u2713 5 * 3 = " + resultado1 + " (esperado: 15)");

        int resultado2 = calculadora.executarMultiplicacao(-5, 3);
        assert resultado2 == -15 : "Erro: -5 * 3 deveria ser -15, mas foi " + resultado2;
        System.out.println("\u2713 -5 * 3 = " + resultado2 + " (esperado: -15)");

        int resultado3 = calculadora.executarMultiplicacao(0, 100);
        assert resultado3 == 0 : "Erro: 0 * 100 deveria ser 0, mas foi " + resultado3;
        System.out.println("\u2713 0 * 100 = " + resultado3 + " (esperado: 0)");

        int resultado4 = calculadora.executarMultiplicacao(-4, -5);
        assert resultado4 == 20 : "Erro: -4 * -5 deveria ser 20, mas foi " + resultado4;
        System.out.println("\u2713 -4 * -5 = " + resultado4 + " (esperado: 20)");

        System.out.println("Todos os testes de multiplicação passaram!");
    }

    public void testDividir() {
        System.out.println("\n=== Teste: Divisão ===");

        int resultado1 = calculadora.executarDivisao(10, 2);
        assert resultado1 == 5 : "Erro: 10 / 2 deveria ser 5, mas foi " + resultado1;
        System.out.println("\u2713 10 / 2 = " + resultado1 + " (esperado: 5)");

        int resultado2 = calculadora.executarDivisao(15, 3);
        assert resultado2 == 5 : "Erro: 15 / 3 deveria ser 5, mas foi " + resultado2;
        System.out.println("\u2713 15 / 3 = " + resultado2 + " (esperado: 5)");

        int resultado3 = calculadora.executarDivisao(-10, 2);
        assert resultado3 == -5 : "Erro: -10 / 2 deveria ser -5, mas foi " + resultado3;
        System.out.println("\u2713 -10 / 2 = " + resultado3 + " (esperado: -5)");

        int resultado4 = calculadora.executarDivisao(7, 2);
        assert resultado4 == 3 : "Erro: 7 / 2 deveria ser 3 (divisão inteira), mas foi " + resultado4;
        System.out.println("\u2713 7 / 2 = " + resultado4 + " (esperado: 3 - divisão inteira)");

        System.out.println("Todos os testes de divisão passaram!");
    }

    public void testDividirPorZero() {
        System.out.println("\n=== Teste: Divisão por Zero ===");

        try {
            calculadora.executarDivisao(10, 0);
            assert false : "Erro: Deveria lançar ArithmeticException ao dividir por zero";
        } catch (ArithmeticException e) {
            System.out.println("\u2713 Excecução lançada corretamente: " + e.getMessage());
        }

        System.out.println("Teste de divisão por zero passou!");
    }

    public void runAllTests() {
        setUp();

        try {
            testAdicionar();
            testSubtrair();
            testMultiplicar();
            testDividir();
            testDividirPorZero();

            System.out.println("\n" + "=".repeat(50));
            System.out.println("TODOS OS TESTES PASSARAM COM SUCESSO!");
            System.out.println("=".repeat(50));
        } catch (AssertionError e) {
            System.err.println("\n" + "=".repeat(50));
            System.err.println("FALHA NO TESTE: " + e.getMessage());
            System.err.println("=".repeat(50));
            throw e;
        }
    }

    public static void main(String[] args) {
        CalculadoraTest test = new CalculadoraTest();
        test.runAllTests();
    }
}
