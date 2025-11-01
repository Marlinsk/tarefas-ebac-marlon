package org.example.project.test;

import org.example.project.test.dao.*;
import org.example.project.test.service.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ClienteDAOServiceTest.class,
        EstoqueProdutoTest.class,
        CarrinhoIntegracaoTest.class,
        ProdutoDAOTest.class,
        VendaDAOTest.class,
        NotaFiscalDAOTest.class,
        ClienteServiceTest.class,
        ProdutoServiceTest.class,
        EstoqueServiceTest.class,
        VendaServiceTest.class,
        NotaFiscalServiceTest.class,
        CarrinhoItemServiceTest.class
})
public class AllTests {
    public static void main(String[] args) {
        JUnitCore core = new JUnitCore();

        core.addListener(new RunListener() {
            private long startSuite;
            private long startTest;

            @Override
            public void testRunStarted(org.junit.runner.Description description) {
                startSuite = System.currentTimeMillis();
                System.out.println(">>> Iniciando suíte: " + description.getDisplayName());
            }

            @Override
            public void testStarted(org.junit.runner.Description description) {
                startTest = System.currentTimeMillis();
                System.out.println("----> Iniciando teste: " + description.getClassName() + "." + description.getMethodName());
            }

            @Override
            public void testFinished(org.junit.runner.Description description) {
                long duration = System.currentTimeMillis() - startTest;
                System.out.println("<---- Finalizado: " + description.getMethodName() + " (" + duration + " ms)");
            }

            @Override
            public void testFailure(Failure failure) {
                System.err.println("XXXX Falha: " + failure.getDescription() + " - " + failure.getMessage());
            }

            @Override
            public void testRunFinished(Result result) {
                long duration = System.currentTimeMillis() - startSuite;
                System.out.println(">>> Suíte finalizada");
                System.out.println("   Total de testes: " + result.getRunCount());
                System.out.println("   Falhas: " + result.getFailureCount());
                System.out.println("   Ignorados: " + result.getIgnoreCount());
                System.out.println("   Tempo total: " + duration + " ms");
            }
        });

        core.run(AllTests.class);
    }
}
