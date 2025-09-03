package org.example.dao;

import org.example.application.dao.VendaDAO;
import org.example.application.domain.Venda;
import org.example.builder.ClienteBuilder;
import org.example.builder.ProdutoBuilder;
import org.example.builder.VendaBuilder;
import org.example.infra.BaseTest;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class VendaDAOTest extends BaseTest {

    @Test
    public void deveBuscarPorClientePeriodoFaixaEProduto() {
        VendaDAO dao = new VendaDAO();

        Venda v1 = VendaBuilder.umaVenda().comCodigo("V1").comCliente(ClienteBuilder.umCliente().comCpf(1L).build()).build();
        v1.adicionarProduto(ProdutoBuilder.umProduto().comCodigo("P1").comValor(10).build(), 3); // 30
        v1.setDataCriacao(LocalDateTime.now().minusDays(3));
        assertTrue(dao.cadastrar(v1));

        Venda v2 = VendaBuilder.umaVenda().comCodigo("V2")
                .comCliente(ClienteBuilder.umCliente().comCpf(2L).build()).build();
        v2.adicionarProduto(ProdutoBuilder.umProduto().comCodigo("P2").comValor(50).build(), 1); // 50
        v2.setDataCriacao(LocalDateTime.now().minusDays(1));
        assertTrue(dao.cadastrar(v2));

        assertEquals(1, dao.buscarPorCliente(1L).size());
        assertEquals(2, dao.buscarPorPeriodo(LocalDateTime.now().minusDays(5), LocalDateTime.now()).size());
        assertEquals(1, dao.buscarPorFaixaDeValor(BigDecimal.valueOf(40), BigDecimal.valueOf(60)).size());
        assertEquals(1, dao.buscarPorProduto("P1").size());
    }
}
