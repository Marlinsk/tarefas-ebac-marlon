package org.example.project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.project.domain.entities.Marca;
import org.example.project.repositories.MarcaRepository;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class MarcaRepositoryCrudTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private MarcaRepository marcaRepository;

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
        marcaRepository = new MarcaRepository(entityManager);
    }

    @After
    public void tearDown() {
        if (entityManager != null && entityManager.isOpen()) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            entityManager.close();
        }
    }

    private Marca novaMarca(String nome, String pais) {
        Marca m = new Marca();
        m.setNome(nome);
        m.setPaisOrigem(pais);
        return m;
    }

    @Test
    public void deveCriarELerMarca() {
        entityManager.getTransaction().begin();
        Marca m = marcaRepository.salvar(novaMarca("Orion Motors", "Alemanha"));
        entityManager.getTransaction().commit();

        assertNotNull(m.getId());

        Marca encontrado = marcaRepository.buscarPorId(m.getId());
        assertNotNull(encontrado);
        assertEquals("Orion Motors", encontrado.getNome());
        assertEquals("Alemanha", encontrado.getPaisOrigem());
    }

    @Test
    public void deveAtualizarMarca() {
        entityManager.getTransaction().begin();
        Marca m = marcaRepository.salvar(novaMarca("Altair", "Japão"));
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        m.setPaisOrigem("EUA");
        Marca up = marcaRepository.atualizar(m);
        entityManager.getTransaction().commit();

        Marca encontrado = marcaRepository.buscarPorId(up.getId());
        assertEquals("EUA", encontrado.getPaisOrigem());
    }

    @Test
    public void deveRemoverMarca() {
        entityManager.getTransaction().begin();
        Marca m = marcaRepository.salvar(novaMarca("Vega", "Brasil"));
        Long id = m.getId();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        marcaRepository.remover(m);
        entityManager.getTransaction().commit();

        assertNull(marcaRepository.buscarPorId(id));
    }

    @Test
    public void deveListarEContarMarcas() {
        entityManager.getTransaction().begin();
        marcaRepository.salvar(novaMarca("Lyra", "Itália"));
        marcaRepository.salvar(novaMarca("Cygnus", "França"));
        entityManager.getTransaction().commit();

        List<Marca> lista = marcaRepository.listarTodos();
        assertTrue(lista.size() >= 2);

        long total = marcaRepository.contar();
        assertTrue(total >= 2);
    }

    @Test
    public void deveBuscarPorNome() {
        entityManager.getTransaction().begin();
        marcaRepository.salvar(novaMarca("Cassiopeia", "Espanha"));
        entityManager.getTransaction().commit();

        Marca achou = marcaRepository.buscarPorNome("Cassiopeia");
        assertNotNull(achou);
        assertEquals("Espanha", achou.getPaisOrigem());
    }
}
