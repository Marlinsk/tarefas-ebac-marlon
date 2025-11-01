package org.example.mocks.test.services;

import org.example.application.domain.Venda;
import org.example.application.services.VendaService;
import org.example.builder.ClienteBuilder;
import org.example.builder.ProdutoBuilder;
import org.example.builder.VendaBuilder;
import org.example.mocks.dao.MockVendaDAO;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class VendaServiceMockTest {

    @Test
    public void deveFiltrarVendasComMockDAO() {
        MockVendaDAO mockDAO = new MockVendaDAO();
        VendaService service = new VendaService(mockDAO);

        Venda v1 = VendaBuilder.umaVenda().comCodigo("VX1").comCliente(ClienteBuilder.umCliente().comCpf(10L).build()).build();
        v1.registrarProdutoNaVenda(ProdutoBuilder.umProduto().comCodigo("PP1").comValor(30).build(), 1);
        v1.setDataCriacao(LocalDateTime.now().minusDays(2));
        service.cadastrar(v1);

        Venda v2 = VendaBuilder.umaVenda().comCodigo("VX2").comCliente(ClienteBuilder.umCliente().comCpf(20L).build()).build();
        v2.registrarProdutoNaVenda(ProdutoBuilder.umProduto().comCodigo("PP2").comValor(60).build(), 1);
        v2.setDataCriacao(LocalDateTime.now().minusHours(12));
        service.cadastrar(v2);

        assertEquals(1, service.buscarPorCliente(10L).size());
        assertEquals(2, service.buscarPorPeriodo(LocalDateTime.now().minusDays(3), LocalDateTime.now()).size());
        assertEquals(1, service.buscarPorFaixaDeValor(BigDecimal.valueOf(50), BigDecimal.valueOf(70)).size());
        assertEquals(1, service.buscarPorProduto("PP1").size());
    }
}
