package org.example.project.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.project.domain.entities.Cliente;
import org.example.project.domain.entities.Venda;
import org.example.project.infra.dao.IVendaDAO;
import org.example.project.infra.dao.impl.VendaDAO;
import org.example.project.infra.services.impl.VendaService;
import org.junit.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class VendaDAOTest {

    private static final String PU_NAME = "mod35PU";

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    private static IVendaDAO dao;
    private static VendaService service;

    @BeforeClass
    public static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PU_NAME);
        assertNotNull(entityManagerFactory);
        entityManager = entityManagerFactory.createEntityManager();
        assertNotNull(entityManager);

        dao = new VendaDAO(entityManager);
        service = new VendaService(dao);
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
        List<Venda> list = entityManager.createQuery("select v from Venda v", Venda.class).getResultList();
        assertNotNull(list);
    }

    @Test
    public void test02_crudVendaDAO() {
        Cliente cliente = new Cliente("Levi Carlos Eduardo Erick Pereira", "483.493.037-86", "levicarlospereira@hotmail.fr", "(95) 98672-4535", "Rua Logoa Nova", 921, "Boa Vista", "RR", "Said Salomão", "69310-743");
        entityManager.persist(cliente);
        entityManager.flush();

        Venda nova = new Venda("VD-1001", cliente, LocalDateTime.now(), new BigDecimal("250.00"));
        Venda venda = dao.save(nova);
        entityManager.flush();

        assertNotNull(venda.getId());
        assertNotNull(venda.getCreatedAt());
        assertNotNull(venda.getUpdatedAt());

        var opt = dao.findById(venda.getId());
        assertTrue(opt.isPresent());
        assertEquals("VD-1001", opt.get().getNumero());
        assertEquals(cliente.getId(), opt.get().getCliente().getId());

        assertTrue(dao.exists(venda.getId()));

        venda.setNumero("VD-1001A");
        venda.setTotal(new BigDecimal("299.90"));
    }
}