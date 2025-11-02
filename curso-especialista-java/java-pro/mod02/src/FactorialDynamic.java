import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class FactorialDynamic {
    public static BigInteger topDown(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }

        Map<Integer, BigInteger> memo = new HashMap<>();
        return topDownHelper(n, memo);
    }

    private static BigInteger topDownHelper(int n, Map<Integer, BigInteger> memo) {
        if (n == 0 || n == 1) {
            return BigInteger.ONE;
        }

        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        BigInteger result = BigInteger.valueOf(n).multiply(topDownHelper(n - 1, memo));
        memo.put(n, result);

        return result;
    }

    public static BigInteger bottomUp(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }

        BigInteger[] table = new BigInteger[n + 1];
        table[0] = BigInteger.ONE;

        for (int i = 1; i <= n; i++) {
            table[i] = BigInteger.valueOf(i).multiply(table[i - 1]);
        }

        return table[n];
    }

    public static BigInteger bottomUpOptimized(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }

        BigInteger result = BigInteger.ONE;

        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }

        return result;
    }
}
