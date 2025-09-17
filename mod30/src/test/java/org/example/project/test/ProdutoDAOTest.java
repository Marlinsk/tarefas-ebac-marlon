package org.example.project.test;

import org.example.project.domain.entities.Produto;
import org.example.project.domain.entities.ProdutoCategoria;
import org.example.project.infra.adapters.DatabaseAdapter;
import org.example.project.infra.adapters.JdbcDatabaseAdapter;
import org.example.project.infra.dao.impl.ProdutoDAO;
import org.junit.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class ProdutoDAOTest {

    private static DatabaseAdapter db;
    private static ProdutoDAO dao;

    private static final String URL  = System.getenv().getOrDefault("PG_URL",  "jdbc:postgresql://localhost:5432/ebac-courses-java-backend");
    private static final String USER = System.getenv().getOrDefault("PG_USER", "postgres");
    private static final String PASS = System.getenv().getOrDefault("PG_PASS", "postgres");

    @BeforeClass
    public static void beforeAll() {
        Properties p = new Properties();
        p.setProperty("user", USER);
        p.setProperty("password", PASS);
        db = new JdbcDatabaseAdapter(URL, p) {};
        db.connect();
        assertTrue("Deveria conectar no banco", db.isConnected());

        db.execute("CREATE TABLE IF NOT EXISTS \"Produto\" (" +
                "id SERIAL PRIMARY KEY," +
                "nome TEXT NOT NULL," +
                "codigo TEXT UNIQUE NOT NULL," +
                "descricao TEXT," +
                "valor NUMERIC(18,2) NOT NULL," +
                "created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
                "updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW())");

        db.execute("ALTER TABLE \"Produto\" ADD COLUMN IF NOT EXISTS categoria TEXT");
        db.execute("ALTER TABLE \"Produto\" ADD COLUMN IF NOT EXISTS ativo BOOLEAN DEFAULT TRUE");

        db.execute("ALTER TABLE \"Produto\" DROP CONSTRAINT IF EXISTS chk_produto_categoria");
        db.execute("ALTER TABLE \"Produto\" ADD CONSTRAINT chk_produto_categoria " + "CHECK (categoria IN ('ELETRONICOS','PERIFERICOS','MOVEIS','VESTUARIO','ALIMENTOS','OUTROS'))");


        db.execute("CREATE OR REPLACE FUNCTION set_updated_at() RETURNS TRIGGER AS $$ " + "BEGIN NEW.updated_at := NOW(); RETURN NEW; END; $$ LANGUAGE plpgsql;");
        db.execute("DROP TRIGGER IF EXISTS trg_produto_updated ON \"Produto\"");
        db.execute("CREATE TRIGGER trg_produto_updated BEFORE UPDATE ON \"Produto\" FOR EACH ROW EXECUTE FUNCTION set_updated_at();");

        db.execute("TRUNCATE TABLE \"Produto\" RESTART IDENTITY CASCADE");

        dao = new ProdutoDAO(URL, USER, PASS);
    }

    @AfterClass
    public static void afterAll() {
        if (db != null) db.close();
    }

    @Test
    public void test01_tabelaExiste() {
        String sql = "SELECT COUNT(*) AS total " + "FROM information_schema.tables " + "WHERE table_schema='public' AND table_name='Produto'";
        List<Long> res = db.query(sql, row -> row.get("total", Long.class));
        assertFalse(res.isEmpty());
        assertTrue("Tabela Produto deveria existir", res.get(0) > 0L);
    }

    @Test
    public void test02_crudProdutoDAO() {
        Produto p = new Produto("Teclado Mecânico", "TEC-001", "Switch blue", new BigDecimal("299.90"), ProdutoCategoria.PERIFERICOS, true);
        p = dao.save(p);
        assertNotNull(p.getId());
        assertEquals(ProdutoCategoria.PERIFERICOS, p.getCategoria());
        assertEquals(Boolean.TRUE, p.getAtivo());
        assertNotNull(p.getCreatedAt());
        assertNotNull(p.getUpdatedAt());

        Produto achado = dao.findById(p.getId()).orElseThrow();
        assertEquals("TEC-001", achado.getCodigo());
        assertEquals(ProdutoCategoria.PERIFERICOS, achado.getCategoria());

        assertTrue(dao.findByCodigo("TEC-001").isPresent());

        p.setDescricao("Switch blue ABNT2");
        p.setValor(new BigDecimal("279.90"));
        p.setCategoria(ProdutoCategoria.ELETRONICOS);
        p.setAtivo(false);
        Produto up = dao.update(p);
        assertEquals("Switch blue ABNT2", up.getDescricao());
        assertEquals(0, up.getValor().compareTo(new BigDecimal("279.90")));
        assertEquals(ProdutoCategoria.ELETRONICOS, up.getCategoria());
        assertEquals(Boolean.FALSE, up.getAtivo());

        Timestamp ct = Timestamp.valueOf(up.getCreatedAt());
        Timestamp ut = Timestamp.valueOf(up.getUpdatedAt());
        assertTrue("updated_at deve ser >= created_at", ut.equals(ct) || ut.after(ct));

        assertFalse(dao.findAll().isEmpty());
        assertTrue(dao.exists(up.getId()));

        dao.deleteById(up.getId());
        assertFalse(dao.exists(up.getId()));
        assertFalse(dao.findById(up.getId()).isPresent());
    }

    @Test
    public void test03_codigoDuplicado_noInsert() {
        Produto a = new Produto("Mouse Gamer", "MOU-001", "RGB", new BigDecimal("150.00"), ProdutoCategoria.PERIFERICOS, true);
        a = dao.save(a);
        assertNotNull(a.getId());

        Produto b = new Produto("Mouse Office", "MOU-001", "Simples", new BigDecimal("60.00"), ProdutoCategoria.PERIFERICOS, true);

        try {
            dao.save(b);
            fail("Deveria falhar por código (codigo) duplicado");
        } catch (RuntimeException e) {

        }
    }

    @Test
    public void test04_codigoDuplicado_noUpdate() {
        Produto p1 = new Produto("Monitor 24", "MON-24", "IPS", new BigDecimal("800.00"), ProdutoCategoria.ELETRONICOS, true);
        Produto p2 = new Produto("Monitor 27", "MON-27", "IPS", new BigDecimal("1200.00"), ProdutoCategoria.ELETRONICOS, true);
        p1 = dao.save(p1);
        p2 = dao.save(p2);

        p2.setCodigo("MON-24");

        try {
            dao.update(p2);
            fail("Deveria falhar por código duplicado no UPDATE");
        } catch (RuntimeException e) {
            // OK
        }
    }
}
