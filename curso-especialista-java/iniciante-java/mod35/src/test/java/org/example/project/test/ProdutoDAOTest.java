package org.example.project.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.project.domain.entities.Produto;
import org.example.project.domain.entities.ProdutoCategoria;
import org.example.project.infra.dao.impl.ProdutoDAO;
import org.junit.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

public class ProdutoDAOTest {

    private static final String PU_NAME = "mod35PU";

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static ProdutoDAO dao;

    @BeforeClass
    public static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PU_NAME);
        assertNotNull(entityManagerFactory);
        entityManager = entityManagerFactory.createEntityManager();
        assertNotNull(entityManager);
        dao = new ProdutoDAO(entityManager);
    }

    @AfterClass
    public static void afterAll() {
        if (entityManager != null && entityManager.isOpen()) entityManager.close();
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) entityManagerFactory.close();
    }

    @Before
    public void openTx() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
            entityManager.clear();
        }
        entityManager.getTransaction().begin();
    }

    @After
    public void closeTx() {
        if (entityManager.getTransaction().isActive()) {
            try {
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
                entityManager.clear();
                throw e;
            }
        }
        entityManager.clear();
    }

    @Test
    public void test01_schemaDisponivelPorJPA() {
        List<Produto> list = entityManager.createQuery("select p from Produto p", Produto.class).getResultList();
        assertNotNull(list);
    }

    @Test
    public void test02_crudProdutoDAO() {
        Produto p = new Produto("Teclado Mecânico", "PD-TEC-001", "Switch blue", new BigDecimal("299.90"), ProdutoCategoria.PERIFERICOS, true);
        dao.save(p);
        entityManager.flush();

        assertNotNull(p.getId());
        assertEquals(ProdutoCategoria.PERIFERICOS, p.getCategoria());
        assertEquals(Boolean.TRUE, p.getAtivo());
        assertNotNull(p.getCreatedAt());
        assertNotNull(p.getUpdatedAt());

        Produto achado = dao.findById(p.getId()).orElseThrow();
        assertEquals("PD-TEC-001", achado.getCodigo());
        assertEquals(ProdutoCategoria.PERIFERICOS, achado.getCategoria());
        assertTrue(dao.findByCodigo("PD-TEC-001").isPresent());

        p.setDescricao("Switch blue ABNT2");
        p.setValor(new BigDecimal("279.90"));
        p.setCategoria(ProdutoCategoria.ELETRONICOS);
        p.setAtivo(false);
        Produto up = dao.update(p);
        entityManager.flush();

        assertEquals("Switch blue ABNT2", up.getDescricao());
        assertEquals(0, up.getValor().compareTo(new BigDecimal("279.90")));
        assertEquals(ProdutoCategoria.ELETRONICOS, up.getCategoria());
        assertEquals(Boolean.FALSE, up.getAtivo());

        Timestamp ct = Timestamp.valueOf(up.getCreatedAt());
        Timestamp ut = Timestamp.valueOf(up.getUpdatedAt());
        assertTrue("updated_at deve ser >= created_at", ut.equals(ct) || ut.after(ct));

        assertFalse(dao.findAll().isEmpty());
        assertTrue(dao.exists(up.getId()));

        dao.deleteById(up.getId());
        entityManager.flush();
        assertFalse(dao.exists(up.getId()));
        assertFalse(dao.findById(up.getId()).isPresent());
    }

    @Test
    public void test03_codigoDuplicado_noInsert() {
        Produto a = new Produto("Mouse Gamer", "PD-MOU-001", "RGB", new BigDecimal("150.00"), ProdutoCategoria.PERIFERICOS, true);
        dao.save(a);
        entityManager.flush();
        assertNotNull(a.getId());

        Produto b = new Produto("Mouse Office", "PD-MOU-001", "Simples", new BigDecimal("60.00"), ProdutoCategoria.PERIFERICOS, true);

        try {
            dao.save(b);
            entityManager.flush();
            fail("Deveria falhar por código (codigo) duplicado");
        } catch (RuntimeException expected) {
            entityManager.getTransaction().rollback();
            entityManager.clear();
        }
    }

    @Test
    public void test04_codigoDuplicado_noUpdate() {
        Produto p1 = new Produto("Monitor 24", "PD-MON-24", "IPS", new BigDecimal("800.00"), ProdutoCategoria.ELETRONICOS, true);
        Produto p2 = new Produto("Monitor 27", "PD-MON-27", "IPS", new BigDecimal("1200.00"), ProdutoCategoria.ELETRONICOS, true);

        dao.save(p1);
        dao.save(p2);
        entityManager.flush();

        p2.setCodigo("PD-MON-24");
        try {
            dao.update(p2);
            entityManager.flush();
            fail("Deveria falhar por código duplicado no UPDATE");
        } catch (RuntimeException expected) {
            entityManager.getTransaction().rollback();
            entityManager.clear();
        }
    }
}
