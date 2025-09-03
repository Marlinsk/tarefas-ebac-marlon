package org.example.service;

import org.example.application.dao.ProdutoDAO;
import org.example.application.domain.Produto;
import org.example.application.services.ProdutoService;
import org.example.builder.ProdutoBuilder;
import org.example.infra.BaseTest;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ProdutoServiceTest extends BaseTest {

    @Test
    public void deveBuscarPorNomeDescricaoEPreco() {
        ProdutoDAO dao = new ProdutoDAO();
        ProdutoService service = new ProdutoService(dao);

        Produto p1 = ProdutoBuilder.umProduto().comCodigo("P1").comNome("Leite Integral").comValor(6.5).build();
        Produto p2 = ProdutoBuilder.umProduto().comCodigo("P2").comNome("Leite Desnatado").comValor(6.0).build();
        Produto p3 = ProdutoBuilder.umProduto().comCodigo("P3").comNome("Café Torrado").comDescricao("Moído").comValor(15).build();

        assertTrue(service.cadastrar(p1));
        assertTrue(service.cadastrar(p2));
        assertTrue(service.cadastrar(p3));

        assertNotNull(service.buscarPorNomeExato("leite integral"));
        assertEquals(2, service.buscarPorNomeParcial("leite").size());
        assertEquals(1, service.buscarPorDescricao("moído").size());
        assertEquals(2, service.buscarPorFaixaDePreco(BigDecimal.valueOf(5), BigDecimal.valueOf(7)).size());
        assertTrue(service.existePorNome("Café Torrado"));
    }
}
