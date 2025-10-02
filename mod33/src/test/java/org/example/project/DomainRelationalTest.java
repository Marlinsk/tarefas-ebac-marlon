package org.example.project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.project.domain.entities.Acessorio;
import org.example.project.domain.entities.Carro;
import org.example.project.domain.entities.Marca;
import org.example.project.repositories.AcessorioRepository;
import org.example.project.repositories.CarroRepository;
import org.example.project.repositories.MarcaRepository;
import org.junit.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class DomainRelationalTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private MarcaRepository marcaRepository;
    private CarroRepository carroRepository;
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
        marcaRepository = new MarcaRepository(entityManager);
        carroRepository = new CarroRepository(entityManager);
        acessorioRepository = new AcessorioRepository(entityManager);
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

    private Carro novoCarro(String modelo, int ano, String valor) {
        Carro c = new Carro();
        c.setModelo(modelo);
        c.setAnoFabricacao(ano);
        c.setValor(new BigDecimal(valor));
        return c;
    }

    private Acessorio novoAcessorio(String nome, String preco) {
        Acessorio a = new Acessorio();
        a.setNome(nome);
        a.setPreco(new BigDecimal(preco));
        return a;
    }

    @Test
    public void devePersistirOneToMany_Marca_Carros() {
        entityManager.getTransaction().begin();

        Marca m = novaMarca("Astoria Motors", "Alemanha");
        marcaRepository.salvar(m);

        Carro c1 = novoCarro("Falcon X", 2024, "250000.00");
        Carro c2 = novoCarro("Falcon S", 2025, "280000.00");

        c1.setMarca(m);
        c2.setMarca(m);
        carroRepository.salvar(c1);
        carroRepository.salvar(c2);

        entityManager.getTransaction().commit();

        Marca db = marcaRepository.buscarPorId(m.getId());
        assertNotNull(db);
        List<Carro> carros = carroRepository.listarTodos();
        assertTrue(carros.size() >= 2);
        assertEquals(m.getId(), carros.get(0).getMarca().getId());
    }

    @Test
    public void devePersistirManyToMany_Carro_Acessorios() {
        entityManager.getTransaction().begin();

        Marca m = novaMarca("Vértice", "Brasil");
        marcaRepository.salvar(m);

        Carro c = novoCarro("VTR-900", 2025, "180000.00");
        c.setMarca(m);
        carroRepository.salvar(c);

        Acessorio a1 = novoAcessorio("Som Premium", "4500.00");
        Acessorio a2 = novoAcessorio("Bancos em Couro", "6500.00");
        acessorioRepository.salvar(a1);
        acessorioRepository.salvar(a2);

        c.addAcessorio(a1);
        c.addAcessorio(a2);

        carroRepository.atualizar(c);

        entityManager.getTransaction().commit();

        Carro db = carroRepository.buscarPorId(c.getId());
        assertEquals(2, db.getAcessorios().size());

        Acessorio dbA1 = acessorioRepository.buscarPorId(a1.getId());
        assertTrue(dbA1.getCarros().stream().anyMatch(x -> x.getId().equals(c.getId())));
    }

    @Test
    public void devePersistirOneToOne_Marca_AcessorioPrincipal() {
        entityManager.getTransaction().begin();

        Acessorio principal = novoAcessorio("Aerofólio de Fibra", "3200.00");
        acessorioRepository.salvar(principal);

        Marca m = novaMarca("Helix", "Japão");
        m.setAcessorioPrincipal(principal);
        principal.setMarcaQueUsaComoPrincipal(m);
        marcaRepository.salvar(m);

        entityManager.getTransaction().commit();

        Marca db = marcaRepository.buscarPorId(m.getId());
        assertNotNull(db.getAcessorioPrincipal());
        assertEquals(principal.getId(), db.getAcessorioPrincipal().getId());

        Acessorio dbAcc = acessorioRepository.buscarPorId(principal.getId());
        assertNotNull(dbAcc.getMarcaQueUsaComoPrincipal());
        assertEquals(m.getId(), dbAcc.getMarcaQueUsaComoPrincipal().getId());
    }
}
