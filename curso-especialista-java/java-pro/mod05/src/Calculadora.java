public class Calculadora {

    private int adicionar(int num1, int num2) {
        return num1 + num2;
    }

    private int subtrair(int num1, int num2) {
        return num1 - num2;
    }

    private int multiplicar(int num1, int num2) {
        return num1 * num2;
    }

    private int dividir(int num1, int num2) {
        if (num2 == 0) {
            throw new ArithmeticException("Divis\u00e3o por zero n\u00e3o \u00e9 permitida");
        }
        return num1 / num2;
    }

    public int executarAdicao(int num1, int num2) {
        return adicionar(num1, num2);
    }

    public int executarSubtracao(int num1, int num2) {
        return subtrair(num1, num2);
    }

    public int executarMultiplicacao(int num1, int num2) {
        return multiplicar(num1, num2);
    }

    public int executarDivisao(int num1, int num2) {
        return dividir(num1, num2);
    }
}
