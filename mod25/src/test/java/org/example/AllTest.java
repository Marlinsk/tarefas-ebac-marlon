package org.example;

import org.example.dao.*;
import org.example.mocks.test.services.*;
import org.example.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ClienteDAOTest.class,
        ProdutoDAOTest.class,
        CarrinhoDAOTest.class,
        NotaDAOTest.class,
        VendaDAOTest.class,

        ClienteServiceTest.class,
        ProdutoServiceTest.class,
        CarrinhoServiceTest.class,
        NotaServiceTest.class,
        VendaServiceTest.class,

        ClienteServiceMockTest.class,
        ProdutoServiceMockTest.class,
        CarrinhoServiceMockTest.class,
        NotaServiceMockTest.class,
        VendaServiceMockTest.class
})
public class AllTest { }
