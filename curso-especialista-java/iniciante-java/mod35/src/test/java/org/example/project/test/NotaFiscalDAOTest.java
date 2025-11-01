package org.example.project.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.project.domain.entities.Cliente;
import org.example.project.domain.entities.NotaFiscal;
import org.example.project.domain.entities.Venda;
import org.example.project.infra.dao.INotaFiscalDAO;
import org.example.project.infra.dao.impl.NotaFiscalDAO;
import org.example.project.infra.dao.impl.VendaDAO;
import org.example.project.infra.services.impl.NotaFiscalService;
import org.junit.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class NotaFiscalDAOTest {

    private static final String PU_NAME = "mod35PU";

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    private static VendaDAO vendaDAO;
    private static INotaFiscalDAO dao;
    private static NotaFiscalService service;

    @BeforeClass
    public static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PU_NAME);
        assertNotNull(entityManagerFactory);
        entityManager = entityManagerFactory.createEntityManager();
        assertNotNull(entityManager);

        vendaDAO = new VendaDAO(entityManager);
        dao = new NotaFiscalDAO(entityManager);
        service = new NotaFiscalService(dao);
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
        List<NotaFiscal> list = entityManager.createQuery("select n from NotaFiscal n", NotaFiscal.class).getResultList();
        assertNotNull(list);
    }

    @Test
    public void test02_crudNotaFiscalDAO() {
        Cliente cliente = new Cliente("Andrea Sophia Luzia Nunes", "462.489.160-04", "andrea_nunes@carolpessoa.com.br", "(54) 98674-7366", "Rua Logoa Nova", 921, "Erechim", "RS", "Progresso", "99708-662");
        entityManager.persist(cliente);
        entityManager.flush();

        Venda venda = new Venda("VND-NF-" + System.nanoTime(), cliente, LocalDateTime.now(), new BigDecimal("500.00"));
        vendaDAO.save(venda);
        entityManager.flush();

        String chave = "CHAVE-" + System.nanoTime();
        NotaFiscal nf = new NotaFiscal(chave, "000001", "1", venda, LocalDateTime.now(), new BigDecimal("500.00"));
        NotaFiscal salva = dao.save(nf);
        entityManager.flush();

        assertNotNull(salva.getId());
        assertNotNull(salva.getCreatedAt());
        assertNotNull(salva.getUpdatedAt());

        var opt = dao.findById(salva.getId());
        assertTrue(opt.isPresent());
        assertEquals(chave, opt.get().getChaveAcesso());
        assertTrue(dao.exists(salva.getId()));

        salva.setNumero("000002");
        salva.setValorTotal(new BigDecimal("499.90"));
        NotaFiscal up = dao.update(salva);
        entityManager.flush();

        assertEquals("000002", up.getNumero());
        assertEquals(0, up.getValorTotal().compareTo(new BigDecimal("499.90")));

        Timestamp ct = Timestamp.valueOf(up.getCreatedAt());
        Timestamp ut = Timestamp.valueOf(up.getUpdatedAt());
        assertTrue(ut.equals(ct) || ut.after(ct));

        assertTrue(dao.findByChaveAcesso(chave).isPresent());
        assertFalse(dao.findByVenda(venda.getId()).isEmpty());
        assertFalse(dao.findAll().isEmpty());

        dao.deleteById(salva.getId());
        entityManager.flush();
        assertFalse(dao.exists(salva.getId()));
        assertFalse(dao.findById(salva.getId()).isPresent());
    }

    @Test
    public void test03_crudViaService() {
        Cliente cliente = new Cliente("Lorena Sara Carvalho", "333.913.090-67", "lorena.sara.carvalho@bighost.com.br", "(53) 98371-0255", "Avenida Presidente Juscelino Kubitschek de Oliveira", 217, "Pelotas", "RS", "Areal", "96080-780");
        entityManager.persist(cliente);
        entityManager.flush();

        Venda venda = new Venda("VND-NF-" + System.nanoTime(), cliente, LocalDateTime.now(), new BigDecimal("250.00"));
        vendaDAO.save(venda);
        entityManager.flush();

        String chave = "CHAVE-" + System.nanoTime();
        NotaFiscal nf = new NotaFiscal(chave, "000010", "1", venda, LocalDateTime.now(), new BigDecimal("250.00"));
        nf = service.criar(nf);
        entityManager.flush();
        assertNotNull(nf.getId());

        nf.setValorTotal(new BigDecimal("199.99"));
        nf = service.atualizar(nf);
        entityManager.flush();
        assertEquals(0, nf.getValorTotal().compareTo(new BigDecimal("199.99")));

        assertTrue(service.buscarPorChaveAcesso(chave).isPresent());
        assertFalse(service.listarPorVenda(venda.getId()).isEmpty());

        service.remover(nf.getId());
        entityManager.flush();
        assertFalse(service.buscarPorId(nf.getId()).isPresent());
    }
}
