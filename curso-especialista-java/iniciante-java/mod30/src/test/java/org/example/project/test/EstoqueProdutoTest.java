package org.example.project.test;

import org.example.project.domain.entities.Estoque;
import org.example.project.domain.entities.Produto;
import org.example.project.domain.entities.ProdutoCategoria;
import org.example.project.infra.adapters.DatabaseAdapter;
import org.example.project.infra.adapters.JdbcDatabaseAdapter;
import org.example.project.infra.dao.impl.EstoqueDAO;
import org.example.project.infra.dao.impl.ProdutoDAO;
import org.example.project.infra.services.impl.EstoqueService;
import org.junit.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class EstoqueProdutoTest {

    private static DatabaseAdapter db;
    private static ProdutoDAO produtoDAO;
    private static EstoqueDAO estoqueDAO;

    private static final String URL  = System.getenv().getOrDefault("PG_URL",  "jdbc:postgresql://localhost:5432/ebac-courses-java-backend");
    private static final String USER = System.getenv().getOrDefault("PG_USER", "postgres");
    private static final String PASS = System.getenv().getOrDefault("PG_PASS", "postgres");

    private static Produto produto;

    @BeforeClass
    public static void beforeAll() {
        Properties p = new Properties();
        p.setProperty("user", USER);
        p.setProperty("password", PASS);
        db = new JdbcDatabaseAdapter(URL, p) {};
        db.connect();
        assertTrue(db.isConnected());

        db.execute("CREATE TABLE IF NOT EXISTS \"Produto\" (" + "id SERIAL PRIMARY KEY," + "nome TEXT NOT NULL," + "codigo TEXT UNIQUE NOT NULL," + "descricao TEXT," + "valor NUMERIC(18,2) NOT NULL," + "created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()," + "updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW())");

        db.execute("ALTER TABLE \"Produto\" ADD COLUMN IF NOT EXISTS categoria TEXT");
        db.execute("ALTER TABLE \"Produto\" ADD COLUMN IF NOT EXISTS ativo BOOLEAN DEFAULT TRUE");

        db.execute("CREATE TABLE IF NOT EXISTS \"Estoque\" (" + "id SERIAL PRIMARY KEY," + "produto_id INT NOT NULL REFERENCES \"Produto\"(id)," + "quantidade INT NOT NULL DEFAULT 0," + "created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()," + "updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW())");

        db.execute("CREATE OR REPLACE FUNCTION set_updated_at() RETURNS TRIGGER AS $$ BEGIN NEW.updated_at := NOW(); RETURN NEW; END; $$ LANGUAGE plpgsql;");
        db.execute("DROP TRIGGER IF EXISTS trg_produto_updated ON \"Produto\"");
        db.execute("CREATE TRIGGER trg_produto_updated BEFORE UPDATE ON \"Produto\" FOR EACH ROW EXECUTE FUNCTION set_updated_at();");
        db.execute("DROP TRIGGER IF EXISTS trg_estoque_updated ON \"Estoque\"");
        db.execute("CREATE TRIGGER trg_estoque_updated BEFORE UPDATE ON \"Estoque\" FOR EACH ROW EXECUTE FUNCTION set_updated_at();");

        db.execute("TRUNCATE TABLE \"Estoque\" RESTART IDENTITY CASCADE");
        db.execute("TRUNCATE TABLE \"Produto\" RESTART IDENTITY CASCADE");

        produtoDAO = new ProdutoDAO(URL, USER, PASS);
        estoqueDAO = new EstoqueDAO(URL, USER, PASS);

        produto = new Produto("Teclado Mecânico", "TEC-001", "Teclado switch blue", new BigDecimal("299.90"), ProdutoCategoria.PERIFERICOS, true);
        produto = produtoDAO.save(produto);
        assertNotNull(produto.getId());
        assertEquals(ProdutoCategoria.PERIFERICOS, produto.getCategoria());
        assertEquals(Boolean.TRUE, produto.getAtivo());
    }

    @AfterClass
    public static void afterAll() {
        if (db != null) db.close();
    }

    @Test
    public void test01_crudEstoque() {
        Estoque e = new Estoque(produto.getId(), 0);
        e = estoqueDAO.save(e);
        assertNotNull(e.getId());
        assertEquals(Integer.valueOf(0), e.getQuantidade());

        e.setQuantidade(10);
        Estoque afterIn = estoqueDAO.update(e);
        assertEquals(Integer.valueOf(10), afterIn.getQuantidade());

        afterIn.setQuantidade(7);
        Estoque afterOut = estoqueDAO.update(afterIn);
        assertEquals(Integer.valueOf(7), afterOut.getQuantidade());

        afterOut.setQuantidade(0);
        Estoque afterZero = estoqueDAO.update(afterOut);
        assertEquals(Integer.valueOf(0), afterZero.getQuantidade());

        Timestamp ct = Timestamp.valueOf(afterZero.getCreatedAt());
        Timestamp ut = Timestamp.valueOf(afterZero.getUpdatedAt());
        assertTrue(ut.equals(ct) || ut.after(ct));

        assertFalse(estoqueDAO.findAll().isEmpty());
        estoqueDAO.deleteById(afterZero.getId());
        assertFalse(estoqueDAO.exists(afterZero.getId()));
    }

    @Test
    public void test02_produtoCrudComEnumEAtivo() {
        Produto p2 = new Produto("Headset", "HDS-222", "Headset 7.1", new BigDecimal("499.00"), ProdutoCategoria.ELETRONICOS, false);
        p2 = produtoDAO.save(p2);
        assertNotNull(p2.getId());
        assertEquals(ProdutoCategoria.ELETRONICOS, p2.getCategoria());
        assertEquals(Boolean.FALSE, p2.getAtivo());

        p2.setCategoria(ProdutoCategoria.PERIFERICOS);
        p2.setAtivo(true);
        p2 = produtoDAO.update(p2);
        assertEquals(ProdutoCategoria.PERIFERICOS, p2.getCategoria());
        assertEquals(Boolean.TRUE, p2.getAtivo());

        assertTrue(produtoDAO.findByCodigo("HDS-222").isPresent());
        assertTrue(produtoDAO.exists(p2.getId()));
        assertFalse(produtoDAO.findAll().isEmpty());

        produtoDAO.deleteById(p2.getId());
        assertFalse(produtoDAO.exists(p2.getId()));
    }
}
