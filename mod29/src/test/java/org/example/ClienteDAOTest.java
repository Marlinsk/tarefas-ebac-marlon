package org.example;


import org.example.infra.adapter.DatabaseAdapter;
import org.example.infra.dao.ClienteDAO;
import org.example.infra.database.JdbcDatabaseAdapter;
import org.example.domain.entities.Cliente;
import org.junit.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class ClienteDAOTest {

    private static DatabaseAdapter db;
    private static ClienteDAO dao;

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
                "CREATE TABLE IF NOT EXISTS cliente (" +
                        "  id SERIAL PRIMARY KEY," +
                        "  nome TEXT NOT NULL," +
                        "  email TEXT UNIQUE," +
                        "  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
                        "  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()" +
                        ")";
        db.execute(ddl);

        String fun =
                "CREATE OR REPLACE FUNCTION set_updated_at() " +
                        "RETURNS TRIGGER AS $$ " +
                        "BEGIN " +
                        "  NEW.updated_at := NOW(); " +
                        "  RETURN NEW; " +
                        "END; " +
                        "$$ LANGUAGE plpgsql;";
        db.execute(fun);

        db.execute("DROP TRIGGER IF EXISTS trg_cliente_updated ON cliente");
        String trg =
                "CREATE TRIGGER trg_cliente_updated " +
                        "BEFORE UPDATE ON cliente " +
                        "FOR EACH ROW " +
                        "EXECUTE FUNCTION set_updated_at()";
        db.execute(trg);

        db.execute("TRUNCATE TABLE cliente RESTART IDENTITY");

        dao = new ClienteDAO(URL, USER, PASS);
    }

    @Test
    public void test01_conectaEConfereTabela() {
        assertTrue("Conexão deve estar ativa", db.isConnected());

        String sql =
                "SELECT COUNT(*) AS total " +
                        "FROM information_schema.tables " +
                        "WHERE table_schema = 'public' AND table_name = 'cliente'";

        List<Long> res = db.query(sql, row -> row.get("total", Long.class));
        assertFalse("Consulta deveria retornar 1 linha", res.isEmpty());
        assertTrue("Tabela 'cliente' deveria existir", res.get(0) > 0L);
    }

    @Test
    public void test02_crudCompletoClienteDAO() {
        // CREATE
        Cliente novo = new Cliente("Rafael Salles", "rafael@example.com");
        Cliente salvo = dao.save(novo);

        assertNotNull("ID gerado deveria existir", salvo.getId());
        assertNotNull("createdAt deveria vir do banco", salvo.getCreatedAt());
        assertNotNull("updatedAt deveria vir do banco", salvo.getUpdatedAt());

        // READ by ID
        var achadoOpt = dao.findById(salvo.getId());
        assertTrue("Deveria achar por ID", achadoOpt.isPresent());
        Cliente achado = achadoOpt.get();
        assertEquals("Rafael Salles", achado.getNome());
        assertEquals("rafael@example.com", achado.getEmail());

        // exists
        assertTrue("exists(id) deveria ser true", dao.exists(salvo.getId()));

        // UPDATE
        achado.setNome("Rafael S.");
        achado.setEmail("rafael.s@example.com");
        Cliente atualizado = dao.update(achado);

        assertEquals("Rafael S.", atualizado.getNome());
        assertEquals("rafael.s@example.com", atualizado.getEmail());
        assertNotNull("updatedAt deveria estar preenchido após update", atualizado.getUpdatedAt());

        Timestamp ct = Timestamp.valueOf(atualizado.getCreatedAt());
        Timestamp ut = Timestamp.valueOf(atualizado.getUpdatedAt());
        assertTrue("updated_at deve ser >= created_at", ut.equals(ct) || ut.after(ct));

        // LIST
        List<Cliente> todos = dao.findAll();
        assertFalse("findAll não deveria estar vazio", todos.isEmpty());
        assertTrue("Lista deveria conter o cliente", todos.stream().anyMatch(c -> c.getId().equals(salvo.getId())));

        // DELETE
        dao.deleteById(salvo.getId());
        assertFalse("Após delete, exists deve ser false", dao.exists(salvo.getId()));
        assertFalse("Após delete, findById deve ser empty", dao.findById(salvo.getId()).isPresent());
    }
}
