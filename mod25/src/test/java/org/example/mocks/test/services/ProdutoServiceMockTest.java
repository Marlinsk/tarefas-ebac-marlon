package org.example.mocks.test.services;

import org.example.application.domain.Produto;
import org.example.application.services.ProdutoService;
import org.example.builder.ProdutoBuilder;
import org.example.mocks.dao.MockProdutoDAO;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ProdutoServiceMockTest {

    @Test
    public void deveBuscarPorNomeDescricaoEPrecoComMock() {
        MockProdutoDAO mockDAO = new MockProdutoDAO();
        ProdutoService service = new ProdutoService(mockDAO);

        Produto p1 = ProdutoBuilder.umProduto().comCodigo("P1").comNome("Leite Integral").comValor(6.5).build();
        Produto p2 = ProdutoBuilder.umProduto().comCodigo("P2").comNome("Leite Desnatado").comValor(6.0).build();
        Produto p3 = ProdutoBuilder.umProduto().comCodigo("P3").comNome("Café Torrado").comDescricao("Moído").comValor(15).build();

        service.cadastrar(p1);
        service.cadastrar(p2);
        service.cadastrar(p3);

        assertNotNull(service.buscarPorNomeExato("leite integral"));
        assertEquals(2, service.buscarPorNomeParcial("leite").size());
        assertEquals(1, service.buscarPorDescricao("moído").size());
        assertEquals(2, service.buscarPorFaixaDePreco(BigDecimal.valueOf(5), BigDecimal.valueOf(7)).size());
        assertTrue(service.existePorNome("Café Torrado"));
    }
}
