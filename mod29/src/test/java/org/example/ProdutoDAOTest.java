package org.example;

import org.example.infra.adapter.DatabaseAdapter;
import org.example.infra.dao.ProdutoDAO;
import org.example.infra.database.JdbcDatabaseAdapter;
import org.example.domain.entities.Produto;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
    private static final String PASS = System.getenv().getOrDefault("PG_PASS", "070498");

    @BeforeClass
    public static void beforeAll() {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        db = new JdbcDatabaseAdapter(URL, props);
        db.connect();
        assertTrue("Deveria conectar no banco", db.isConnected());

        String ddl =
                "CREATE TABLE IF NOT EXISTS produto (" +
                        "  id SERIAL PRIMARY KEY," +
                        "  nome TEXT NOT NULL," +
                        "  valor NUMERIC(18,2) NOT NULL," +
                        "  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
                        "  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()" +
                        ")";
        db.execute(ddl);

        String fun =
                "CREATE OR REPLACE FUNCTION set_updated_at() " +
                        "RETURNS TRIGGER AS $$ " +
                        "BEGIN NEW.updated_at := NOW(); RETURN NEW; END; $$ LANGUAGE plpgsql;";
        db.execute(fun);

        db.execute("DROP TRIGGER IF EXISTS trg_produto_updated ON produto");
        String trg =
                "CREATE TRIGGER trg_produto_updated " +
                        "BEFORE UPDATE ON produto " +
                        "FOR EACH ROW EXECUTE FUNCTION set_updated_at()";
        db.execute(trg);

        db.execute("TRUNCATE TABLE produto RESTART IDENTITY");

        dao = new ProdutoDAO(URL, USER, PASS);
    }

    @AfterClass
    public static void afterAll() {
        if (db != null) db.close(); // disconnect
    }

    @Test
    public void test01_conectaEConfereTabela() {
        assertTrue("Conexão deve estar ativa", db.isConnected());

        String sql =
                "SELECT COUNT(*) AS total " +
                        "FROM information_schema.tables " +
                        "WHERE table_schema = 'public' AND table_name = 'produto'";

        List<Long> res = db.query(sql, row -> row.get("total", Long.class));
        assertFalse("Consulta deveria retornar 1 linha", res.isEmpty());
        assertTrue("Tabela produto deveria existir", res.get(0) > 0L);
    }

    @Test
    public void test02_crudCompletoProdutoDAO() {
        // CREATE
        Produto novo = new Produto("Teclado Mecânico", new BigDecimal("350.00"));
        Produto salvo = dao.save(novo);

        assertNotNull("ID gerado deveria existir", salvo.getId());
        assertNotNull("createdAt deveria vir do banco", salvo.getCreatedAt());
        assertNotNull("updatedAt deveria vir do banco", salvo.getUpdatedAt());
        assertEquals(0, salvo.getValor().compareTo(new BigDecimal("350.00")));

        // READ by ID
        var achadoOpt = dao.findById(salvo.getId());
        assertTrue("Deveria achar por ID", achadoOpt.isPresent());
        Produto achado = achadoOpt.get();
        assertEquals("Teclado Mecânico", achado.getNome());
        assertEquals(0, achado.getValor().compareTo(new BigDecimal("350.00")));

        // exists
        assertTrue("exists(id) deveria ser true", dao.exists(salvo.getId()));

        // UPDATE
        achado.setNome("Teclado Mecânico RGB");
        achado.setValor(new BigDecimal("399.90"));
        Produto atualizado = dao.update(achado);

        assertEquals("Teclado Mecânico RGB", atualizado.getNome());
        assertEquals(0, atualizado.getValor().compareTo(new BigDecimal("399.90")));
        assertNotNull("updatedAt deveria estar preenchido após update", atualizado.getUpdatedAt());

        Timestamp ct = Timestamp.valueOf(atualizado.getCreatedAt());
        Timestamp ut = Timestamp.valueOf(atualizado.getUpdatedAt());
        assertTrue("updated_at deve ser >= created_at", ut.equals(ct) || ut.after(ct));

        // LIST
        List<Produto> todos = dao.findAll();
        assertFalse("findAll não deveria estar vazio", todos.isEmpty());
        assertTrue("Lista deveria conter o produto", todos.stream().anyMatch(p -> p.getId().equals(salvo.getId())));

        // DELETE
        dao.deleteById(salvo.getId());
        assertFalse("Após delete, exists deve ser false", dao.exists(salvo.getId()));
        assertFalse("Após delete, findById deve ser empty", dao.findById(salvo.getId()).isPresent());
    }
}
