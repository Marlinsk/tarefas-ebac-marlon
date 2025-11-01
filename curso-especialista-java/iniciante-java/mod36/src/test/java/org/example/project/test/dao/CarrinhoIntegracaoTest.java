package org.example.project.test.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.project.domain.entities.*;
import org.example.project.infra.dao.impl.CarrinhoDAO;
import org.example.project.infra.dao.impl.CarrinhoItemDAO;
import org.example.project.infra.dao.impl.ProdutoDAO;
import org.example.project.infra.services.impl.CarrinhoService;
import org.junit.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.Assert.*;

public class CarrinhoIntegracaoTest {

    private static final String PU_NAME = "mod36PU";

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    private static CarrinhoDAO carrinhoDAO;
    private static CarrinhoItemDAO itemDAO;
    private static ProdutoDAO produtoDAO;
    private static CarrinhoService carrinhoService;

    private static Cliente cliente;
    private static Produto prod1;
    private static Produto prod2;
    private static Carrinho carrinho;

    @BeforeClass
    public static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PU_NAME);
        assertNotNull(entityManagerFactory);
        entityManager = entityManagerFactory.createEntityManager();
        assertNotNull(entityManager);

        carrinhoDAO = new CarrinhoDAO(entityManager);
        itemDAO = new CarrinhoItemDAO(entityManager);
        produtoDAO = new ProdutoDAO(entityManager);
        carrinhoService = new CarrinhoService(carrinhoDAO, itemDAO);

        entityManager.getTransaction().begin();

        entityManager.createQuery("delete from CarrinhoItem").executeUpdate();
        entityManager.createQuery("delete from Carrinho").executeUpdate();
        entityManager.createQuery("delete from NotaFiscal").executeUpdate();
        entityManager.createQuery("delete from Venda").executeUpdate();
        entityManager.createQuery("delete from Estoque").executeUpdate();
        entityManager.createQuery("delete from Produto").executeUpdate();
        entityManager.createQuery("delete from Cliente c where c.cpf = :cpf").setParameter("cpf", "123.456.789-00").executeUpdate();

        cliente = new Cliente("Cliente Carrinho", "123.456.789-00", "cli.carrinho@example.com", "11999990000", "Rua Teste", 100, "Sampa", "SP", "Centro", "01000-000");
        entityManager.persist(cliente);

        prod1 = new Produto("Monitor 27", "MON-27", "Monitor IPS 27", new BigDecimal("1200.00"), ProdutoCategoria.ELETRONICOS, true);
        prod2 = new Produto("Mouse Gamer", "MOU-01", "Mouse RGB", new BigDecimal("150.50"), ProdutoCategoria.PERIFERICOS, true);
        entityManager.persist(prod1);
        entityManager.persist(prod2);

        carrinho = new Carrinho("CARR-001", cliente, "ABERTO", BigDecimal.ZERO);
        entityManager.persist(carrinho);

        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    @AfterClass
    public static void afterAll() {
        if (entityManager != null && entityManager.isOpen()) entityManager.close();
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) entityManagerFactory.close();
    }

    @Test
    public void test01_fluxoCarrinhoComEnumCategoria() {
        entityManager.getTransaction().begin();

        carrinho = entityManager.find(Carrinho.class, carrinho.getId());
        prod1 = entityManager.find(Produto.class,  prod1.getId());
        prod2 = entityManager.find(Produto.class,  prod2.getId());

        CarrinhoItem i1 = new CarrinhoItem(carrinho, prod1, 1, prod1.getValor(), prod1.getValor());
        itemDAO.save(i1);

        carrinho.setTotal(itemDAO.sumSubtotalByCarrinho(carrinho.getId()));
        carrinhoDAO.update(carrinho);
        entityManager.flush();
        assertEquals(0, carrinho.getTotal().compareTo(new BigDecimal("1200.00")));

        CarrinhoItem i2 = new CarrinhoItem(carrinho, prod2, 2, prod2.getValor(), prod2.getValor().multiply(new BigDecimal("2")));

        itemDAO.save(i2);

        carrinho.setTotal(itemDAO.sumSubtotalByCarrinho(carrinho.getId()));
        carrinhoDAO.update(carrinho);
        entityManager.flush();
        assertEquals(0, carrinho.getTotal().compareTo(new BigDecimal("1501.00")));

        Produto p1db = produtoDAO.findById(prod1.getId()).orElseThrow();
        Produto p2db = produtoDAO.findById(prod2.getId()).orElseThrow();
        assertEquals(ProdutoCategoria.ELETRONICOS, p1db.getCategoria());
        assertEquals(ProdutoCategoria.PERIFERICOS, p2db.getCategoria());

        i2.setQuantidade(3);
        i2.setSubtotal(prod2.getValor().multiply(new BigDecimal("3")));
        itemDAO.update(i2);

        carrinho.setTotal(itemDAO.sumSubtotalByCarrinho(carrinho.getId()));
        carrinhoDAO.update(carrinho);
        entityManager.flush();
        assertEquals(0, carrinho.getTotal().compareTo(new BigDecimal("1651.50")));

        Carrinho rec = carrinhoDAO.findById(carrinho.getId()).orElseThrow();
        Timestamp ct = Timestamp.valueOf(rec.getCreatedAt());
        Timestamp ut = Timestamp.valueOf(rec.getUpdatedAt());
        assertTrue(ut.equals(ct) || ut.after(ct));

        itemDAO.deleteByCarrinhoAndProduto(carrinho.getId(), prod1.getId());
        carrinho.setTotal(itemDAO.sumSubtotalByCarrinho(carrinho.getId()));
        carrinhoDAO.update(carrinho);
        entityManager.flush();
        assertEquals(0, carrinho.getTotal().compareTo(new BigDecimal("451.50")));

        itemDAO.deleteByCarrinhoAndProduto(carrinho.getId(), prod2.getId());
        carrinho.setTotal(itemDAO.sumSubtotalByCarrinho(carrinho.getId()));
        carrinhoDAO.update(carrinho);
        entityManager.flush();
        assertEquals(0, carrinho.getTotal().compareTo(BigDecimal.ZERO));
        assertTrue(itemDAO.findByCarrinho(carrinho.getId()).isEmpty());

        entityManager.getTransaction().commit();
        entityManager.clear();
    }


    @Test
    public void test02_listarEBuscarCarrinho() {
        entityManager.getTransaction().begin();

        Carrinho c2 = new Carrinho("CARR-002", cliente, "ABERTO", BigDecimal.ZERO);
        carrinhoDAO.save(c2);
        entityManager.flush();

        assertTrue(carrinhoDAO.findByCodigo("CARR-002").isPresent());
        assertFalse(carrinhoDAO.findByCliente(cliente.getId()).isEmpty());

        carrinhoDAO.deleteById(c2.getId());
        entityManager.flush();
        assertFalse(carrinhoDAO.exists(c2.getId()));

        entityManager.getTransaction().commit();
        entityManager.clear();
    }
}
