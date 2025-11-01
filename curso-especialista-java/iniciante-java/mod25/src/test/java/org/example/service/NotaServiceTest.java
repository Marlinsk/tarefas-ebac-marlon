package org.example.service;

import org.example.application.dao.NotaDAO;
import org.example.application.domain.Nota;
import org.example.application.domain.Venda;
import org.example.application.services.NotaService;
import org.example.builder.ClienteBuilder;
import org.example.builder.ProdutoBuilder;
import org.example.builder.VendaBuilder;
import org.example.infra.BaseTest;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class NotaServiceTest extends BaseTest {

    @Test
    public void deveBuscarPorChaveSerieVendaEPeriodo() {
        NotaDAO dao = new NotaDAO();
        NotaService service = new NotaService(dao);

        Venda v = VendaBuilder.umaVenda().comCodigo("V-77").comCliente(ClienteBuilder.umCliente().comCpf(77L).build()).build();
        v.registrarProdutoNaVenda(ProdutoBuilder.umProduto().comCodigo("P1").comValor(10).build(), 2);

        Nota n1 = new Nota("N77-1", "S10", "CH-77-1", LocalDateTime.now().minusHours(5), v.getValorTotal(), v);
        Nota n2 = new Nota("N77-2", "S10", "CH-77-2", LocalDateTime.now().minusHours(2), BigDecimal.valueOf(50), v);

        assertTrue(service.cadastrar(n1));
        assertTrue(service.cadastrar(n2));

        assertNotNull(service.buscarPorChaveAcesso("ch-77-2"));
        assertEquals(2, service.buscarPorSerie("s10").size());
        assertEquals(2, service.buscarPorVenda("v-77").size());
        assertEquals(2, service.buscarPorPeriodo(LocalDateTime.now().minusDays(1), LocalDateTime.now()).size());
    }
}
