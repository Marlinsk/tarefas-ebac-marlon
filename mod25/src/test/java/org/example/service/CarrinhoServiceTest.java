package org.example.service;

import org.example.application.dao.CarrinhoDAO;
import org.example.application.domain.Carrinho;
import org.example.application.domain.Produto;
import org.example.application.domain.Venda;
import org.example.application.services.CarrinhoService;
import org.example.builder.CarrinhoBuilder;
import org.example.builder.ClienteBuilder;
import org.example.builder.ProdutoBuilder;
import org.example.infra.BaseTest;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CarrinhoServiceTest extends BaseTest {

    @Test
    public void fluxoCompletoCarrinho() {
        CarrinhoDAO dao = new CarrinhoDAO();
        CarrinhoService service = new CarrinhoService(dao);

        Carrinho carrinho = CarrinhoBuilder.umCarrinho().comCliente(ClienteBuilder.umCliente().comCpf(100L).build()).build();
        assertTrue(service.cadastrar(carrinho));

        Produto p1 = ProdutoBuilder.umProduto().comCodigo("P1").comNome("Arroz").comValor(20).build();
        Produto p2 = ProdutoBuilder.umProduto().comCodigo("P2").comNome("Feijao").comValor(10).build();

        service.adicionarProduto(carrinho.getCodigo(), p1, 1);
        service.adicionarProduto(carrinho.getCodigo(), p2, 2);

        assertEquals(3, service.quantidadeTotalItens(carrinho.getCodigo()));
        assertEquals(new BigDecimal("40.00"), service.calcularTotal(carrinho.getCodigo()));
        assertTrue(service.contemProduto(carrinho.getCodigo(), p2));

        Venda venda = service.finalizarVenda(carrinho.getCodigo(), "VEND-123");
        assertNotNull(venda);
        assertEquals(new BigDecimal("40.00"), venda.getValorTotal());
    }
}
