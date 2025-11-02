import java.math.BigInteger;

public class Factorial {

    public static BigInteger calculate(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }

        return calculateRecursive(BigInteger.valueOf(n));
    }

    private static BigInteger calculateRecursive(BigInteger n) {
        if (n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE)) {
            return BigInteger.ONE;
        }

        return n.multiply(calculateRecursive(n.subtract(BigInteger.ONE)));
    }
}
