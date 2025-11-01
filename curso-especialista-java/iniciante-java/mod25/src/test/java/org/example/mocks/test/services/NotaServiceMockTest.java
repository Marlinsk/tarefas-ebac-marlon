package org.example.mocks.test.services;

import org.example.application.domain.Nota;
import org.example.application.domain.Venda;
import org.example.application.services.NotaService;
import org.example.builder.ClienteBuilder;
import org.example.builder.ProdutoBuilder;
import org.example.builder.VendaBuilder;
import org.example.mocks.dao.MockNotaDAO;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class NotaServiceMockTest {

    @Test
    public void deveBuscarNotaPorChaveSerieVendaEPeriodoComMock() {
        MockNotaDAO mockDAO = new MockNotaDAO();
        NotaService service = new NotaService(mockDAO);

        Venda v = VendaBuilder.umaVenda().comCodigo("V-M1")
                .comCliente(ClienteBuilder.umCliente().comCpf(77L).build()).build();
        v.registrarProdutoNaVenda(ProdutoBuilder.umProduto().comCodigo("P1").comValor(10).build(), 2);

        Nota n1 = new Nota("NM1", "S10", "CH-M1", LocalDateTime.now().minusHours(5), v.getValorTotal(), v);
        Nota n2 = new Nota("NM2", "S10", "CH-M2", LocalDateTime.now().minusHours(2), BigDecimal.valueOf(50), v);

        service.cadastrar(n1);
        service.cadastrar(n2);

        assertNotNull(service.buscarPorChaveAcesso("ch-m2"));
        assertEquals(2, service.buscarPorSerie("s10").size());
        assertEquals(2, service.buscarPorVenda("v-m1").size());
        assertEquals(2, service.buscarPorPeriodo(LocalDateTime.now().minusDays(1), LocalDateTime.now()).size());
    }
}
