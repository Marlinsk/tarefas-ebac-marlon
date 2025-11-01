package org.example.infra;

import org.example.application.dao.*;
import org.junit.Before;


public abstract class BaseTest {

    @Before
    public void resetDatabase() {
        new ClienteDAO().limparStore();
        new ProdutoDAO().limparStore();
        new CarrinhoDAO().limparStore();
        new VendaDAO().limparStore();
        new NotaDAO().limparStore();
    }
}
