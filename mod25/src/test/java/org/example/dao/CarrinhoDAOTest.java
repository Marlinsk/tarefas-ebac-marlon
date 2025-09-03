package org.example.dao;

import org.example.application.dao.CarrinhoDAO;
import org.example.application.domain.Carrinho;
import org.example.application.domain.Produto;
import org.example.application.domain.Venda;
import org.example.builder.CarrinhoBuilder;
import org.example.builder.ClienteBuilder;
import org.example.builder.ProdutoBuilder;
import org.example.infra.BaseTest;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CarrinhoDAOTest extends BaseTest {

    @Test
    public void deveAdicionarRemoverProdutosECalcularTotal() {
        CarrinhoDAO dao = new CarrinhoDAO();
        Carrinho c = CarrinhoBuilder.umCarrinho().comCliente(ClienteBuilder.umCliente().comCpf(999L).build()).build();
        assertTrue(dao.cadastrar(c));

        Produto arroz = ProdutoBuilder.umProduto().comCodigo("P1").comNome("Arroz").comValor(20).build();
        Produto feijao = ProdutoBuilder.umProduto().comCodigo("P2").comNome("Feijao").comValor(10).build();

        dao.adicionarProduto(c.getCodigo(), arroz, 2);
        dao.adicionarProduto(c.getCodigo(), feijao, 1);

        BigDecimal total = dao.calcularTotal(c.getCodigo());
        assertEquals(new BigDecimal("50.00"), total);

        dao.removerProduto(c.getCodigo(), arroz, 1);
        assertEquals(new BigDecimal("30.00"), dao.calcularTotal(c.getCodigo()));
    }

    @Test
    public void deveFinalizarVendaELimparCarrinho() {
        CarrinhoDAO dao = new CarrinhoDAO();

        Carrinho c = CarrinhoBuilder.umCarrinho().comCliente(ClienteBuilder.umCliente().comCpf(123L).build()).build();
        assertTrue(dao.cadastrar(c));

        Produto p = ProdutoBuilder.umProduto().comCodigo("PX").comNome("Macarrao").comValor(8.5).build();
        dao.adicionarProduto(c.getCodigo(), p, 3);

        Venda v = dao.finalizarVenda(c.getCodigo(), "VEND-001");
        assertNotNull(v);
        assertEquals("VEND-001", v.getCodigo());
        assertEquals(new BigDecimal("25.50"), v.getValorTotal());
        assertNull(dao.consultar(c.getCodigo()));
    }
}
