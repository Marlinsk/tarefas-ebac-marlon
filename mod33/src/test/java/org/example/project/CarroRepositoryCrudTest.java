package org.example.project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.project.domain.entities.Carro;
import org.example.project.domain.entities.Marca;
import org.example.project.repositories.CarroRepository;
import org.example.project.repositories.MarcaRepository;
import org.junit.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class CarroRepositoryCrudTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private CarroRepository carroRepository;
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
        carroRepository = new CarroRepository(entityManager);
        marcaRepository = new MarcaRepository(entityManager);
    }

    @After
    public void tearDown() {
        if (entityManager != null && entityManager.isOpen()) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            entityManager.close();
        }
    }

    private Marca novaMarcaPersistida(String nome, String pais) {
        entityManager.getTransaction().begin();
        Marca m = new Marca();
        m.setNome(nome);
        m.setPaisOrigem(pais);
        marcaRepository.salvar(m);
        entityManager.getTransaction().commit();
        return m;
    }

    private Carro novoCarro(String modelo, int ano, String valor, Marca marca) {
        Carro c = new Carro();
        c.setModelo(modelo);
        c.setAnoFabricacao(ano);
        c.setValor(new BigDecimal(valor));
        c.setMarca(marca);
        return c;
    }

    @Test
    public void deveCriarELerCarro() {
        Marca m = novaMarcaPersistida("Neon", "Alemanha");

        entityManager.getTransaction().begin();
        Carro c = carroRepository.salvar(novoCarro("N-1000", 2024, "150000.00", m));
        entityManager.getTransaction().commit();

        assertNotNull(c.getId());

        Carro encontrado = carroRepository.buscarPorId(c.getId());
        assertNotNull(encontrado);
        assertEquals("N-1000", encontrado.getModelo());
        assertEquals(0, encontrado.getValor().compareTo(new BigDecimal("150000.00")));
        assertEquals(m.getId(), encontrado.getMarca().getId());
    }

    @Test
    public void deveAtualizarCarro() {
        Marca m = novaMarcaPersistida("Quartz", "Japão");

        entityManager.getTransaction().begin();
        Carro c = carroRepository.salvar(novoCarro("QZ-7", 2023, "120000.00", m));
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        c.setModelo("QZ-7 Pro");
        c.setValor(new BigDecimal("132500.90"));
        Carro up = carroRepository.atualizar(c);
        entityManager.getTransaction().commit();

        Carro encontrado = carroRepository.buscarPorId(up.getId());
        assertEquals("QZ-7 Pro", encontrado.getModelo());
        assertEquals(0, encontrado.getValor().compareTo(new BigDecimal("132500.90")));
    }

    @Test
    public void deveRemoverCarro() {
        Marca m = novaMarcaPersistida("Solaris", "EUA");

        entityManager.getTransaction().begin();
        Carro c = carroRepository.salvar(novoCarro("SL-1", 2022, "99000.00", m));
        Long id = c.getId();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        carroRepository.remover(c);
        entityManager.getTransaction().commit();

        assertNull(carroRepository.buscarPorId(id));
    }

    @Test
    public void deveListarEContarCarros() {
        Marca m = novaMarcaPersistida("Orbe", "Itália");

        entityManager.getTransaction().begin();
        carroRepository.salvar(novoCarro("OR-10", 2024, "210000.00", m));
        carroRepository.salvar(novoCarro("OR-12", 2025, "230000.00", m));
        entityManager.getTransaction().commit();

        List<Carro> lista = carroRepository.listarTodos();
        assertTrue(lista.size() >= 2);

        long total = carroRepository.contar();
        assertTrue(total >= 2);
    }

    @Test
    public void deveBuscarPorModelo() {
        Marca m = novaMarcaPersistida("Aurora", "França");

        entityManager.getTransaction().begin();
        carroRepository.salvar(novoCarro("AR-9", 2024, "175000.00", m));
        carroRepository.salvar(novoCarro("AR-9", 2025, "189000.00", m));
        entityManager.getTransaction().commit();

        List<Carro> encontrados = carroRepository.buscarPorModelo("AR-9");
        assertEquals(2, encontrados.size());
    }
}
