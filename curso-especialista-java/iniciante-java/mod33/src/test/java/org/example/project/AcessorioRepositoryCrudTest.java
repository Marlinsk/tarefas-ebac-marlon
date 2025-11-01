package org.example.project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.project.domain.entities.Acessorio;
import org.example.project.repositories.AcessorioRepository;
import org.junit.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class AcessorioRepositoryCrudTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private AcessorioRepository acessorioRepository;

    @BeforeClass
    public static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory("mod33PU");
        assertNotNull(entityManagerFactory);
    }

    @AfterClass
    public static void afterAll() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) entityManagerFactory.close();
    }

    @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        assertNotNull(entityManager);
        acessorioRepository = new AcessorioRepository(entityManager);
    }

    @After
    public void tearDown() {
        if (entityManager != null && entityManager.isOpen()) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            entityManager.close();
        }
    }

    private Acessorio novoAcessorio(String nome, String preco) {
        Acessorio a = new Acessorio();
        a.setNome(nome);
        a.setPreco(new BigDecimal(preco));
        return a;
    }

    @Test
    public void deveCriarELerAcessorio() {
        entityManager.getTransaction().begin();
        Acessorio a = acessorioRepository.salvar(novoAcessorio("Teto Solar", "4500.00"));
        entityManager.getTransaction().commit();

        assertNotNull(a.getId());

        Acessorio encontrado = acessorioRepository.buscarPorId(a.getId());
        assertNotNull(encontrado);
        assertEquals("Teto Solar", encontrado.getNome());
        assertEquals(0, encontrado.getPreco().compareTo(new BigDecimal("4500.00")));
    }

    @Test
    public void deveAtualizarAcessorio() {
        entityManager.getTransaction().begin();
        Acessorio a = acessorioRepository.salvar(novoAcessorio("Kit Multimídia", "3200.00"));
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        a.setPreco(new BigDecimal("3599.90"));
        a.setNome("Kit Multimídia Premium");
        Acessorio up = acessorioRepository.atualizar(a);
        entityManager.getTransaction().commit();

        Acessorio encontrado = acessorioRepository.buscarPorId(up.getId());
        assertEquals("Kit Multimídia Premium", encontrado.getNome());
        assertEquals(0, encontrado.getPreco().compareTo(new BigDecimal("3599.90")));
    }

    @Test
    public void deveRemoverAcessorio() {
        entityManager.getTransaction().begin();
        Acessorio a = acessorioRepository.salvar(novoAcessorio("Aerofólio", "1500.00"));
        Long id = a.getId();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        acessorioRepository.remover(a);
        entityManager.getTransaction().commit();

        assertNull(acessorioRepository.buscarPorId(id));
    }

    @Test
    public void deveListarEContarAcessorios() {
        entityManager.getTransaction().begin();
        acessorioRepository.salvar(novoAcessorio("Bancos em Couro", "5800.00"));
        acessorioRepository.salvar(novoAcessorio("Sensor de Estacionamento", "990.00"));
        entityManager.getTransaction().commit();

        List<Acessorio> lista = acessorioRepository.listarTodos();
        assertTrue(lista.size() >= 2);

        long total = acessorioRepository.contar();
        assertTrue(total >= 2);
    }

    @Test
    public void deveBuscarPorNome() {
        entityManager.getTransaction().begin();
        acessorioRepository.salvar(novoAcessorio("Som Premium", "4200.00"));
        entityManager.getTransaction().commit();

        Acessorio a = acessorioRepository.buscarPorNome("Som Premium");
        assertNotNull(a);
        assertEquals(0, a.getPreco().compareTo(new BigDecimal("4200.00")));
    }
}
