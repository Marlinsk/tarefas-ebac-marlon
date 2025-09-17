package org.example.project.test;

import org.example.project.domain.entities.Carrinho;
import org.example.project.domain.entities.CarrinhoItem;
import org.example.project.domain.entities.Produto;
import org.example.project.infra.adapters.DatabaseAdapter;
import org.example.project.infra.adapters.JdbcDatabaseAdapter;
import org.example.project.infra.dao.impl.CarrinhoDAO;
import org.example.project.infra.dao.impl.CarrinhoItemDAO;
import org.example.project.infra.dao.impl.ProdutoDAO;
import org.example.project.infra.services.impl.CarrinhoService;
import org.junit.*;

import java.math.BigDecimal;
import java.util.List;
import java.sql.Timestamp;
import java.util.Properties;

import static org.junit.Assert.*;

public class CarrinhoIntegracaoTest {

    private static DatabaseAdapter db;

    private static CarrinhoService carrinhoService;
    private static CarrinhoDAO carrinhoDAO;
    private static CarrinhoItemDAO itemDAO;
    private static ProdutoDAO produtoDAO;

    private static final String URL  = System.getenv().getOrDefault("PG_URL",  "jdbc:postgresql://localhost:5432/ebac-courses-java-backend");
    private static final String USER = System.getenv().getOrDefault("PG_USER", "postgres");
    private static final String PASS = System.getenv().getOrDefault("PG_PASS", "postgres");

    private static Integer clienteId;
    private static Produto prod1;
    private static Produto prod2;
    private static Carrinho carrinho;

    @BeforeClass
    public static void beforeAll() {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        db = new JdbcDatabaseAdapter(URL, props) {};
        db.connect();
        assertTrue("Deveria conectar no banco", db.isConnected());

        db.execute("CREATE TABLE IF NOT EXISTS \"Cliente\" (" +
                "id SERIAL PRIMARY KEY, nome TEXT NOT NULL, cpf TEXT UNIQUE, email TEXT UNIQUE, " +
                "created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
                "updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW())");

        db.execute("CREATE TABLE IF NOT EXISTS \"Produto\" (" +
                "id SERIAL PRIMARY KEY," +
                "nome TEXT NOT NULL," +
                "codigo TEXT UNIQUE NOT NULL," +
                "descricao TEXT," +
                "valor NUMERIC(18,2) NOT NULL," +
                "created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
                "updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW())");

        db.execute("CREATE TABLE IF NOT EXISTS \"Carrinho\" (" +
                "id SERIAL PRIMARY KEY," +
                "codigo TEXT UNIQUE NOT NULL," +
                "cliente_id INT NOT NULL REFERENCES \"Cliente\"(id)," +
                "status TEXT NOT NULL," +
                "total NUMERIC(18,2) NOT NULL DEFAULT 0," +
                "created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
                "updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW())");

        db.execute("CREATE TABLE IF NOT EXISTS \"CarrinhoItem\" (" +
                "id SERIAL PRIMARY KEY," +
                "carrinho_id INT NOT NULL REFERENCES \"Carrinho\"(id) ON DELETE CASCADE," +
                "produto_id INT NOT NULL REFERENCES \"Produto\"(id)," +
                "quantidade INT NOT NULL," +
                "preco_unitario NUMERIC(18,2) NOT NULL," +
                "subtotal NUMERIC(18,2) NOT NULL," +
                "created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
                "updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW())");

        db.execute("CREATE OR REPLACE FUNCTION set_updated_at() RETURNS TRIGGER AS $$ BEGIN NEW.updated_at := NOW(); RETURN NEW; END; $$ LANGUAGE plpgsql;");
        db.execute("DROP TRIGGER IF EXISTS trg_produto_updated ON \"Produto\";");
        db.execute("CREATE TRIGGER trg_produto_updated  BEFORE UPDATE ON \"Produto\"      FOR EACH ROW EXECUTE FUNCTION set_updated_at();");
        db.execute("DROP TRIGGER IF EXISTS trg_carrinho_updated ON \"Carrinho\";");
        db.execute("CREATE TRIGGER trg_carrinho_updated BEFORE UPDATE ON \"Carrinho\"     FOR EACH ROW EXECUTE FUNCTION set_updated_at();");
        db.execute("DROP TRIGGER IF EXISTS trg_carrinhoitem_updated ON \"CarrinhoItem\";");
        db.execute("CREATE TRIGGER trg_carrinhoitem_updated BEFORE UPDATE ON \"CarrinhoItem\" FOR EACH ROW EXECUTE FUNCTION set_updated_at();");
        db.execute("DROP TRIGGER IF EXISTS trg_cliente_updated ON \"Cliente\";");
        db.execute("CREATE TRIGGER trg_cliente_updated BEFORE UPDATE ON \"Cliente\"      FOR EACH ROW EXECUTE FUNCTION set_updated_at();");

        db.execute("TRUNCATE TABLE \"CarrinhoItem\" RESTART IDENTITY CASCADE");
        db.execute("TRUNCATE TABLE \"Carrinho\" RESTART IDENTITY CASCADE");
        db.execute("TRUNCATE TABLE \"Produto\" RESTART IDENTITY CASCADE");
        db.execute("TRUNCATE TABLE \"Cliente\" RESTART IDENTITY CASCADE");

        List<Integer> ids = db.query(
                "INSERT INTO \"Cliente\" (nome, email) VALUES (?, ?) RETURNING id",
                row -> row.get("id", Integer.class),
                "Cliente Carrinho", "cli.carrinho@example.com");
        clienteId = ids.get(0);

        produtoDAO = new ProdutoDAO(URL, USER, PASS);
        carrinhoDAO = new CarrinhoDAO(URL, USER, PASS);
        itemDAO    = new CarrinhoItemDAO(URL, USER, PASS);
        carrinhoService = new CarrinhoService(carrinhoDAO, itemDAO);

        prod1 = new Produto("Monitor 27\"", "MON-27", "Monitor IPS 27", new BigDecimal("1200.00"));
        prod2 = new Produto("Mouse Gamer",  "MOU-01", "Mouse RGB",     new BigDecimal("150.50"));
        prod1 = produtoDAO.save(prod1);
        prod2 = produtoDAO.save(prod2);

        carrinho = new Carrinho("CARR-001", clienteId, "ABERTO", BigDecimal.ZERO);
        carrinho = carrinhoDAO.save(carrinho);
    }

    @AfterClass
    public static void afterAll() {
        if (db != null) db.close();
    }

    @Test
    public void test01_adicionarAtualizarRemoverItensERecalcularTotal() {
        carrinho = carrinhoService.adicionarItem(carrinho.getId(), prod1.getId(), 1, prod1.getValor());
        assertEquals(0, carrinho.getTotal().compareTo(new BigDecimal("1200.00")));

        carrinho = carrinhoService.adicionarItem(carrinho.getId(), prod2.getId(), 2, prod2.getValor());
        assertEquals(0, carrinho.getTotal().compareTo(new BigDecimal("1501.00")));

        List<CarrinhoItem> itens = itemDAO.findByCarrinho(carrinho.getId());
        assertEquals(2, itens.size());

        carrinho = carrinhoService.atualizarQuantidade(carrinho.getId(), prod2.getId(), 3, prod2.getValor());
        assertEquals(0, carrinho.getTotal().compareTo(new BigDecimal("1651.50")));

        Carrinho rec = carrinhoDAO.findById(carrinho.getId()).get();
        Timestamp ct = Timestamp.valueOf(rec.getCreatedAt());
        Timestamp ut = Timestamp.valueOf(rec.getUpdatedAt());
        assertTrue("updated_at deve ser >= created_at", ut.equals(ct) || ut.after(ct));

        carrinho = carrinhoService.removerItem(carrinho.getId(), prod1.getId());
        assertEquals(0, carrinho.getTotal().compareTo(new BigDecimal("451.50")));

        itens = itemDAO.findByCarrinho(carrinho.getId());
        assertEquals(1, itens.size());

        carrinho = carrinhoService.atualizarQuantidade(carrinho.getId(), prod2.getId(), 0, prod2.getValor());
        assertEquals(0, carrinho.getTotal().compareTo(BigDecimal.ZERO));
        assertTrue(itemDAO.findByCarrinho(carrinho.getId()).isEmpty());
    }

    @Test
    public void test02_listarEBuscarCarrinho() {
        Carrinho c2 = new Carrinho("CARR-002", clienteId, "ABERTO", BigDecimal.ZERO);
        c2 = carrinhoDAO.save(c2);

        assertTrue(carrinhoDAO.findByCodigo("CARR-002").isPresent());
        assertFalse(carrinhoDAO.findByCliente(clienteId).isEmpty());

        carrinhoDAO.deleteById(c2.getId());
        assertFalse(carrinhoDAO.exists(c2.getId()));
    }
}
