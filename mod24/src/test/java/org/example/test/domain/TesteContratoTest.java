package org.example.test.domain;

import org.junit.Assert;
import org.junit.Test;

public class TesteContratoTest {

    @Test
    public void deveDefinirENaoAlterarNumero() {
        TesteContrato tc = new TesteContrato();
        tc.setNumero(123);
        Assert.assertEquals(123, tc.getNumero());
    }
}
