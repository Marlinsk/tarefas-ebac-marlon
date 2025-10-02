package org.example.project.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ClienteDAOServiceTest.class,
        EstoqueProdutoTest.class,
        CarrinhoIntegracaoTest.class,
        ProdutoDAOTest.class,
        VendaDAOTest.class,
        NotaFiscalDAOTest.class
})
public class AllTests { }
