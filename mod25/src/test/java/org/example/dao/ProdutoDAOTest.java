package org.example.dao;

import org.example.application.dao.ProdutoDAO;
import org.example.application.domain.Produto;
import org.example.builder.ProdutoBuilder;
import org.example.infra.BaseTest;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class ProdutoDAOTest extends BaseTest {

    @Test
    public void deveCadastrarEConsultarProduto() {
        ProdutoDAO dao = new ProdutoDAO();
        Produto p = ProdutoBuilder.umProduto().comCodigo("P10").comNome("Feijao").comValor(12.5).build();

        assertTrue(dao.cadastrar(p));
        Produto salvo = dao.consultar("P10");
        assertNotNull(salvo);
        assertEquals("Feijao", salvo.getNome());
    }

    @Test
    public void deveBuscarPorNomeDescricaoEPreco() {
        ProdutoDAO dao = new ProdutoDAO();
        dao.cadastrar(ProdutoBuilder.umProduto().comCodigo("P1").comNome("Arroz Branco").comDescricao("Tipo 1").comValor(20).build());
        dao.cadastrar(ProdutoBuilder.umProduto().comCodigo("P2").comNome("Arroz Integral").comDescricao("Saudável").comValor(25).build());
        dao.cadastrar(ProdutoBuilder.umProduto().comCodigo("P3").comNome("Feijao Preto").comDescricao("Tipo 1").comValor(10).build());

        assertNotNull(dao.buscarPorNomeExato("arroz integral"));
        assertEquals(2, dao.buscarPorNomeParcial("arroz").size());
        assertEquals(2, dao.buscarPorDescricao("tipo 1").size());
        List<Produto> faixa = dao.buscarPorFaixaDePreco(BigDecimal.valueOf(10), BigDecimal.valueOf(22));
        assertEquals(2, faixa.size());
        assertTrue(dao.existePorNome("Feijao Preto"));
    }
}
