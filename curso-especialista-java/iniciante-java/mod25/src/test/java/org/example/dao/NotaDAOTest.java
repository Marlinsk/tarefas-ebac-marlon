package org.example.dao;

import org.example.application.dao.NotaDAO;
import org.example.application.domain.Nota;
import org.example.application.domain.Venda;
import org.example.builder.ClienteBuilder;
import org.example.builder.ProdutoBuilder;
import org.example.builder.VendaBuilder;
import org.example.infra.BaseTest;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class NotaDAOTest extends BaseTest {

    @Test
    public void deveBuscarPorChaveSerieVendaEPeriodo() {
        Venda v = VendaBuilder.umaVenda().comCodigo("V-1").comCliente(ClienteBuilder.umCliente().comCpf(1L).build()).build();
        v.registrarProdutoNaVenda(ProdutoBuilder.umProduto().comCodigo("P1").comValor(10).build(), 2);
        Nota n1 = new Nota("N1", "S1", "CH-1", LocalDateTime.now().minusDays(2), v.getValorTotal(), v);
        Nota n2 = new Nota("N2", "S1", "CH-2", LocalDateTime.now().minusDays(1), BigDecimal.valueOf(50), v);

        NotaDAO dao = new NotaDAO();
        assertTrue(dao.cadastrar(n1));
        assertTrue(dao.cadastrar(n2));

        assertNotNull(dao.buscarPorChaveAcesso("ch-2"));
        assertEquals(2, dao.buscarPorSerie("s1").size());
        assertEquals(2, dao.buscarPorVenda("v-1").size());
        List<Nota> periodo = dao.buscarPorPeriodo(LocalDateTime.now().minusDays(3), LocalDateTime.now());
        assertEquals(2, periodo.size());
    }
}
