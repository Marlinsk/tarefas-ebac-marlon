package org.example.project.test;

import org.example.project.domain.entities.Cliente;
import org.example.project.common.exceptions.DatabaseException;
import org.example.project.infra.adapters.DatabaseAdapter;
import org.example.project.infra.adapters.JdbcDatabaseAdapter;
import org.example.project.infra.dao.impl.ClienteDAO;
import org.example.project.infra.services.impl.ClienteService;
import org.junit.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class ClienteDAOServiceTest {

    private static DatabaseAdapter db;
    private static ClienteDAO dao;
    private static ClienteService service;

    private static final String URL  = System.getenv().getOrDefault("PG_URL",  "jdbc:postgresql://localhost:5432/ebac-courses-java-backend");
    private static final String USER = System.getenv().getOrDefault("PG_USER", "postgres");
    private static final String PASS = System.getenv().getOrDefault("PG_PASS", "postgres");

    @BeforeClass
    public static void beforeAll() {
        Properties p = new Properties();
        p.setProperty("user", USER);
        p.setProperty("password", PASS);
        db = new JdbcDatabaseAdapter(URL, p);
        db.connect();
        assertTrue(db.isConnected());

        db.execute("CREATE TABLE IF NOT EXISTS \"Cliente\" (" +
                "id SERIAL PRIMARY KEY," +
                "nome TEXT NOT NULL," +
                "cpf TEXT UNIQUE," +
                "email TEXT UNIQUE," +
                "created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
                "updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW())");

        db.execute("CREATE UNIQUE INDEX IF NOT EXISTS uq_cliente_cpf ON \"Cliente\"(cpf)");

        db.execute("ALTER TABLE \"Cliente\" ADD COLUMN IF NOT EXISTS cpf TEXT");
        db.execute("ALTER TABLE \"Cliente\" ADD COLUMN IF NOT EXISTS tel TEXT");
        db.execute("ALTER TABLE \"Cliente\" ADD COLUMN IF NOT EXISTS endereco TEXT");
        db.execute("ALTER TABLE \"Cliente\" ADD COLUMN IF NOT EXISTS numero INT");
        db.execute("ALTER TABLE \"Cliente\" ADD COLUMN IF NOT EXISTS cidade TEXT");
        db.execute("ALTER TABLE \"Cliente\" ADD COLUMN IF NOT EXISTS estado TEXT");
        db.execute("ALTER TABLE \"Cliente\" ADD COLUMN IF NOT EXISTS bairro TEXT");
        db.execute("ALTER TABLE \"Cliente\" ADD COLUMN IF NOT EXISTS cep TEXT");

        db.execute("CREATE OR REPLACE FUNCTION set_updated_at() RETURNS TRIGGER AS $$ BEGIN NEW.updated_at := NOW(); RETURN NEW; END; $$ LANGUAGE plpgsql;");
        db.execute("DROP TRIGGER IF EXISTS trg_cliente_updated ON \"Cliente\"");
        db.execute("CREATE TRIGGER trg_cliente_updated BEFORE UPDATE ON \"Cliente\" FOR EACH ROW EXECUTE FUNCTION set_updated_at();");

        db.execute("TRUNCATE TABLE \"Cliente\" RESTART IDENTITY CASCADE");

        dao = new ClienteDAO(URL, USER, PASS);
        service = new ClienteService(dao);
    }

    @AfterClass
    public static void afterAll() {
        if (db != null) db.close();
    }

    @Test
    public void test01_tabelaExiste() {
        String sql = "SELECT COUNT(*) AS total FROM information_schema.tables WHERE table_schema='public' AND table_name='Cliente'";
        List<Long> res = db.query(sql, row -> row.get("total", Long.class));
        assertFalse(res.isEmpty());
        assertTrue(res.get(0) > 0L);
    }

    @Test
    public void test02_crudDAO() {
        Cliente c = new Cliente("João Silva", "421.678.098-11", "joao.silva@example.com", "(51) 2987-0030", "Rua das Bananeiras", 477, "Sapucaia do Sul", "RS", "Zoológico", "93211-708");
        c = dao.save(c);
        assertNotNull(c.getId());
        assertNotNull(c.getCreatedAt());
        assertNotNull(c.getUpdatedAt());

        Cliente achado = dao.findById(c.getId()).orElseThrow();
        assertEquals("João Silva", achado.getNome());
        assertEquals("joao.silva@example.com", achado.getEmail());
        assertEquals("421.678.098-11", achado.getCpf());

        achado.setNome("João S.");
        achado.setEmail("joao.s@example.com");
        achado.setCpf("421.678.098-30");
        Cliente up = dao.update(achado);
        assertEquals("João S.", up.getNome());
        assertEquals("joao.s@example.com", up.getEmail());
        assertEquals("421.678.098-30", up.getCpf());

        Timestamp ct = Timestamp.valueOf(up.getCreatedAt());
        Timestamp ut = Timestamp.valueOf(up.getUpdatedAt());
        assertTrue(ut.equals(ct) || ut.after(ct));

        assertFalse(dao.findAll().isEmpty());
        assertTrue(dao.exists(up.getId()));

        dao.deleteById(up.getId());
        assertFalse(dao.exists(up.getId()));
        assertFalse(dao.findById(up.getId()).isPresent());
    }

    @Test
    public void test03_crudService() {
        Cliente c = new Cliente("Maria Oliveira", "678.987.456-26", "maria.oliveira@example.com", "(55) 98998-8735", "Avenida Getúlio Vargas 1250", 204, "Doutor Maurício Cardoso", "RS", "Centro", "98925-970");
        c = service.criar(c);
        assertNotNull(c.getId());

        c.setNome("Maria O.");
        c = service.atualizar(c);
        assertEquals("Maria O.", c.getNome());

        assertTrue(service.buscarPorId(c.getId()).isPresent());
        assertFalse(service.listar().isEmpty());

        service.remover(c.getId());
        assertFalse(service.buscarPorId(c.getId()).isPresent());
    }
}
