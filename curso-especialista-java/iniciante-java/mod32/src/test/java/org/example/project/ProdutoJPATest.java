package org.example.project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.project.dao.ProdutoDao;
import org.example.project.domain.entities.Produto;
import org.junit.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class ProdutoJPATest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private ProdutoDao dao;

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("mod32PU");
        assertNotNull("EntityManagerFactory não deve ser nulo", emf);
    }

    @AfterClass
    public static void tearDownClass() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        assertNotNull("EntityManager não deve ser nulo", em);
        dao = new ProdutoDao(em);
    }

    @After
    public void tearDown() {
        if (em != null && em.isOpen()) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    private Produto novoProduto(String codigo, String nome, String valor) {
        Produto p = new Produto();
        p.setCodigo(codigo);
        p.setNome(nome);
        p.setValor(new BigDecimal(valor));
        p.setDescricao("Descrição de " + nome);
        return p;
    }

    @Test
    public void deveSalvarProduto() {
        em.getTransaction().begin();

        Produto p = novoProduto("COD001", "Teclado Mecânico", "350.00");
        dao.salvar(p);

        em.getTransaction().commit();

        assertNotNull("ID deve ser gerado", p.getId());
        assertTrue("Total deve ser >= 1", dao.contar() >= 1);
    }

    @Test
    public void deveBuscarPorCodigo() {
        em.getTransaction().begin();
        dao.salvar(novoProduto("MSE123", "Mouse Gamer", "199.90"));
        em.getTransaction().commit();

        Produto encontrado = dao.buscarPorCodigo("MSE123");
        assertNotNull(encontrado);
        assertEquals("MSE123", encontrado.getCodigo());
        assertEquals(new BigDecimal("199.90"), encontrado.getValor());
    }

    @Test
    public void deveAtualizarProduto() {
        em.getTransaction().begin();
        Produto p = dao.salvar(novoProduto("HST001", "Headset", "499.00"));
        em.getTransaction().commit();

        em.getTransaction().begin();
        p.setNome("Headset Pro");
        p.setValor(new BigDecimal("549.90"));
        Produto atualizado = dao.atualizar(p);
        em.getTransaction().commit();

        Produto check = dao.buscarPorId(atualizado.getId());
        assertEquals("Headset Pro", check.getNome());
        assertEquals(new BigDecimal("549.90"), check.getValor());
    }

    @Test
    public void deveListarProdutos() {
        em.getTransaction().begin();
        dao.salvar(novoProduto("MPD900", "Mousepad XL", "89.90"));
        dao.salvar(novoProduto("KBC002", "Kit Cabos", "59.50"));
        em.getTransaction().commit();

        List<Produto> lista = dao.listarTodos();
        assertTrue("Deve listar pelo menos 2 produtos", lista.size() >= 2);
    }

    @Test
    public void deveRemoverProduto() {
        em.getTransaction().begin();
        Produto p = dao.salvar(novoProduto("RMV777", "Produto Removível", "10.00"));
        Long id = p.getId();
        em.getTransaction().commit();

        em.getTransaction().begin();
        dao.remover(p);
        em.getTransaction().commit();

        Produto apagado = dao.buscarPorId(id);
        assertNull("Produto deve ter sido removido", apagado);
    }
}
