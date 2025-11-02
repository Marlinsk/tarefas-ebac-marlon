import java.math.BigInteger;

public class ExecuteTest {

    public void executeFactorialTests() {
        long startTime, endTime, duration;

        System.out.println("=== RECURSIVE APPROACH ===");
        System.out.println("Testing factorial calculation:");

        startTime = System.nanoTime();
        BigInteger result5 = Factorial.calculate(5);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("5! = " + result5 + " (Total digits: " + result5.toString().length() + " Execution time: " + duration + " ns)");

        startTime = System.nanoTime();
        BigInteger result10 = Factorial.calculate(10);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("10! = " + result10 + " (Total digits: " + result10.toString().length() + " Execution time: " + duration + " ns)");

        startTime = System.nanoTime();
        BigInteger result20 = Factorial.calculate(20);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("20! = " + result20 + " (Total digits: " + result20.toString().length() + " Execution time: " + duration + " ns)");

        System.out.println("\nTesting with numbers above 100:");

        startTime = System.nanoTime();
        BigInteger result100 = Factorial.calculate(100);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("100! = " + result100 + " (Total digits: " + result100.toString().length() + " Execution time: " + duration + " ns)");

        startTime = System.nanoTime();
        BigInteger result120 = Factorial.calculate(120);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("120! = " + result120 + " (Total digits: " + result120.toString().length() + " Execution time: " + duration + " ns)");

        startTime = System.nanoTime();
        BigInteger result150 = Factorial.calculate(150);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("150! = " + result150 + " (Total digits: " + result150.toString().length() + " Execution time: " + duration + " ns)");

        System.out.println("\n=== DYNAMIC PROGRAMMING - TOP-DOWN (MEMOIZATION) ===");

        startTime = System.nanoTime();
        BigInteger resultTopDown5 = FactorialDynamic.topDown(5);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("5! = " + resultTopDown5 + " (Total digits: " + resultTopDown5.toString().length() + " Execution time: " + duration + " ns)");

        startTime = System.nanoTime();
        BigInteger resultTopDown100 = FactorialDynamic.topDown(100);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("100! (Total digits: " + resultTopDown100.toString().length() + " Execution time: " + duration + " ns)");

        startTime = System.nanoTime();
        BigInteger resultTopDown150 = FactorialDynamic.topDown(150);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("150! (Total digits: " + resultTopDown150.toString().length() + " Execution time: " + duration + " ns)");

        System.out.println("\n=== DYNAMIC PROGRAMMING - BOTTOM-UP (TABULATION) ===");

        startTime = System.nanoTime();
        BigInteger resultBottomUp5 = FactorialDynamic.bottomUp(5);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("5! = " + resultBottomUp5 + " (Total digits: " + resultBottomUp5.toString().length() + " Execution time: " + duration + " ns)");

        startTime = System.nanoTime();
        BigInteger resultBottomUp100 = FactorialDynamic.bottomUp(100);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("100! (Total digits: " + resultBottomUp100.toString().length() + " Execution time: " + duration + " ns)");

        startTime = System.nanoTime();
        BigInteger resultBottomUp150 = FactorialDynamic.bottomUp(150);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("150! (Total digits: " + resultBottomUp150.toString().length() + " Execution time: " + duration + " ns)");

        System.out.println("\n=== DYNAMIC PROGRAMMING - BOTTOM-UP OPTIMIZED (O(1) SPACE) ===");

        startTime = System.nanoTime();
        BigInteger resultOptimized5 = FactorialDynamic.bottomUpOptimized(5);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("5! = " + resultOptimized5 + " (Total digits: " + resultOptimized5.toString().length() + " Execution time: " + duration + " ns)");

        startTime = System.nanoTime();
        BigInteger resultOptimized100 = FactorialDynamic.bottomUpOptimized(100);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("100! (Total digits: " + resultOptimized100.toString().length() + " Execution time: " + duration + " ns)");

        startTime = System.nanoTime();
        BigInteger resultOptimized150 = FactorialDynamic.bottomUpOptimized(150);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("150! (Total digits: " + resultOptimized150.toString().length() + " Execution time: " + duration + " ns)");
    }
}
