package org.example.project.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.project.domain.entities.Estoque;
import org.example.project.domain.entities.Produto;
import org.example.project.domain.entities.ProdutoCategoria;
import org.example.project.infra.dao.impl.EstoqueDAO;
import org.example.project.infra.dao.impl.ProdutoDAO;
import org.junit.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.Assert.*;

public class EstoqueProdutoTest {

    private static final String PU_NAME = "mod35PU";

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    private static ProdutoDAO produtoDAO;
    private static EstoqueDAO estoqueDAO;

    private static Produto produto;

    @BeforeClass
    public static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PU_NAME);
        assertNotNull(entityManagerFactory);
        entityManager = entityManagerFactory.createEntityManager();
        assertNotNull(entityManager);

        produtoDAO = new ProdutoDAO(entityManager);
        estoqueDAO = new EstoqueDAO(entityManager);

        entityManager.getTransaction().begin();

        entityManager.createQuery("delete from Estoque").executeUpdate();
        entityManager.createQuery("delete from Produto p where p.codigo = :cod").setParameter("cod", "TEC-001").executeUpdate();

        String codigoUnico = "TEC-001-" + System.currentTimeMillis();

        produto = new Produto("Teclado Mecânico", codigoUnico, "Teclado switch blue", new BigDecimal("299.90"), ProdutoCategoria.PERIFERICOS, true);
        produtoDAO.save(produto);

        entityManager.getTransaction().commit();
        entityManager.clear();

        assertNotNull(produto.getId());
        assertEquals(ProdutoCategoria.PERIFERICOS, produto.getCategoria());
        assertEquals(Boolean.TRUE, produto.getAtivo());
    }

    @AfterClass
    public static void afterAll() {
        if (entityManager != null && entityManager.isOpen()) entityManager.close();
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) entityManagerFactory.close();
    }

    @Test
    public void test01_crudEstoque() {
        entityManager.getTransaction().begin();

        produto = entityManager.find(Produto.class, produto.getId());

        Estoque e = new Estoque();
        e.setProduto(produto);
        e.setQuantidade(0);
        estoqueDAO.save(e);
        entityManager.flush();

        assertNotNull(e.getId());
        assertEquals(Integer.valueOf(0), e.getQuantidade());

        e.setQuantidade(10);
        Estoque afterIn = estoqueDAO.update(e);
        entityManager.flush();
        assertEquals(Integer.valueOf(10), afterIn.getQuantidade());

        afterIn.setQuantidade(7);
        Estoque afterOut = estoqueDAO.update(afterIn);
        entityManager.flush();
        assertEquals(Integer.valueOf(7), afterOut.getQuantidade());

        afterOut.setQuantidade(0);
        Estoque afterZero = estoqueDAO.update(afterOut);
        entityManager.flush();
        assertEquals(Integer.valueOf(0), afterZero.getQuantidade());

        Timestamp ct = Timestamp.valueOf(afterZero.getCreatedAt());
        Timestamp ut = Timestamp.valueOf(afterZero.getUpdatedAt());
        assertTrue(ut.equals(ct) || ut.after(ct));

        assertFalse(estoqueDAO.findAll().isEmpty());

        estoqueDAO.deleteById(afterZero.getId());
        entityManager.flush();
        assertFalse(estoqueDAO.exists(afterZero.getId()));

        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @Test
    public void test02_produtoCrudComEnumEAtivo() {
        entityManager.getTransaction().begin();

        Produto p2 = new Produto("Headset", "HDS-222", "Headset 7.1", new BigDecimal("499.00"), ProdutoCategoria.ELETRONICOS, false);
        produtoDAO.save(p2);
        entityManager.flush();
        assertNotNull(p2.getId());
        assertEquals(ProdutoCategoria.ELETRONICOS, p2.getCategoria());
        assertEquals(Boolean.FALSE, p2.getAtivo());

        p2.setCategoria(ProdutoCategoria.PERIFERICOS);
        p2.setAtivo(true);
        p2 = produtoDAO.update(p2);
        entityManager.flush();
        assertEquals(ProdutoCategoria.PERIFERICOS, p2.getCategoria());
        assertEquals(Boolean.TRUE, p2.getAtivo());

        assertTrue(produtoDAO.findByCodigo("HDS-222").isPresent());
        assertTrue(produtoDAO.exists(p2.getId()));
        assertFalse(produtoDAO.findAll().isEmpty());

        produtoDAO.deleteById(p2.getId());
        entityManager.flush();
        assertFalse(produtoDAO.exists(p2.getId()));

        entityManager.getTransaction().commit();
        entityManager.clear();
    }
}
