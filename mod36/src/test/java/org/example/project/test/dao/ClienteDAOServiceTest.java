package org.example.project.test.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.project.domain.entities.Cliente;
import org.example.project.infra.dao.impl.ClienteDAO;
import org.example.project.infra.services.impl.ClienteService;
import org.junit.*;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

public class ClienteDAOServiceTest {

    private static final String PU_NAME = "mod36PU";

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    private static ClienteDAO dao;
    private static ClienteService service;

    @BeforeClass
    public static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PU_NAME);
        assertNotNull(entityManagerFactory);
        entityManager = entityManagerFactory.createEntityManager();
        assertNotNull(entityManager);

        dao = new ClienteDAO(entityManager);
        service = new ClienteService(dao);
    }

    @AfterClass
    public static void afterAll() {
        if (entityManager != null && entityManager.isOpen()) entityManager.close();
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) entityManagerFactory.close();
    }

    @Test
    public void test01_schemaDisponivelPorJPA() {
        entityManager.getTransaction().begin();
        List<Cliente> list = entityManager.createQuery("select c from Cliente c", Cliente.class).getResultList();
        entityManager.getTransaction().commit();
        assertNotNull(list);
    }

    @Test
    public void test02_crudDAO() {
        entityManager.getTransaction().begin();

        Cliente c = new Cliente("João Silva", "421.678.098-11", "joao.silva@example.com", "(51) 2987-0030", "Rua das Bananeiras", 477, "Sapucaia do Sul", "RS", "Zoológico", "93211-708");

        c = dao.save(c);
        entityManager.flush();
        assertNotNull(c.getId());
        assertNotNull(c.getCreatedAt());
        assertNotNull(c.getUpdatedAt());

        Cliente achado = dao.findById(c.getId()).orElseThrow();
        assertEquals("João Silva", achado.getNome());
        assertEquals("joao.silva@example.com", achado.getEmail());
        assertEquals("421.678.098-11", achado.getCpf());

        achado.setNome("João S.");
        achado.setEmail("joao.s@example.com");
        achado.setCpf("421.678.098-30");
        Cliente up = dao.update(achado);
        entityManager.flush();

        assertEquals("João S.", up.getNome());
        assertEquals("joao.s@example.com", up.getEmail());
        assertEquals("421.678.098-30", up.getCpf());

        Timestamp ct = Timestamp.valueOf(up.getCreatedAt());
        Timestamp ut = Timestamp.valueOf(up.getUpdatedAt());
        assertTrue(ut.equals(ct) || ut.after(ct));

        assertFalse(dao.findAll().isEmpty());
        assertTrue(dao.exists(up.getId()));

        dao.deleteById(up.getId());
        entityManager.flush();
        assertFalse(dao.exists(up.getId()));
        assertFalse(dao.findById(up.getId()).isPresent());

        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @Test
    public void test03_crudService() {
        entityManager.getTransaction().begin();

        Cliente c = new Cliente("Maria Oliveira", "678.987.456-26", "maria.oliveira@example.com", "(55) 98998-8735", "Avenida Getúlio Vargas 1250", 204, "Doutor Maurício Cardoso", "RS", "Centro", "98925-970");

        c = service.criar(c);
        entityManager.flush();
        assertNotNull(c.getId());

        c.setNome("Maria O.");
        c = service.atualizar(c);
        entityManager.flush();
        assertEquals("Maria O.", c.getNome());

        assertTrue(service.buscarPorId(c.getId()).isPresent());
        assertFalse(service.listar().isEmpty());

        service.remover(c.getId());
        entityManager.flush();
        assertFalse(service.buscarPorId(c.getId()).isPresent());

        entityManager.getTransaction().commit();
        entityManager.clear();
    }
}
