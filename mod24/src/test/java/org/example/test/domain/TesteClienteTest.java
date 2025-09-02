package org.example.test.domain;

import org.junit.Assert;
import org.junit.Test;

public class TesteClienteTest {

    @Test
    public void deveAtribuirNome() {
        TesteCliente cli = new TesteCliente();
        cli.adicionarNome("Rodrigo");
        cli.adicionarNome1("Rodrigo");

        Assert.assertEquals("Rodrigo", cli.getNome());
    }
}
