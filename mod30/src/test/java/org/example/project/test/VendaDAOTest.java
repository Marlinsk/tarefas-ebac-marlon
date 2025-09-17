package org.example.project.test;

import org.example.project.domain.entities.Venda;
import org.example.project.infra.adapters.DatabaseAdapter;
import org.example.project.infra.adapters.JdbcDatabaseAdapter;
import org.example.project.infra.dao.IVendaDAO;
import org.example.project.infra.dao.impl.VendaDAO;
import org.example.project.infra.services.impl.VendaService;
import org.junit.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class VendaDAOTest {

    private static DatabaseAdapter db;
    private static IVendaDAO dao;
    private static VendaService service;

    private static final String URL  = System.getenv().getOrDefault("PG_URL",  "jdbc:postgresql://localhost:5432/ebac-courses-java-backend");
    private static final String USER = System.getenv().getOrDefault("PG_USER", "postgres");
    private static final String PASS = System.getenv().getOrDefault("PG_PASS", "postgres");

    private static Integer clienteId;

    @BeforeClass
    public static void beforeAll() {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        db = new JdbcDatabaseAdapter(URL, props) {};
        db.connect();
        assertTrue("Deveria conectar no banco", db.isConnected());

        db.execute("CREATE TABLE IF NOT EXISTS \"Cliente\" (" +
                "id SERIAL PRIMARY KEY, nome TEXT NOT NULL, cpf TEXT UNIQUE, email TEXT UNIQUE," +
                "created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
                "updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW())");

        db.execute("CREATE TABLE IF NOT EXISTS \"Venda\" (" +
                "id SERIAL PRIMARY KEY," +
                "numero TEXT UNIQUE NOT NULL," +
                "cliente_id INT NOT NULL REFERENCES \"Cliente\"(id)," +
                "data_venda TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
                "total NUMERIC(18,2) NOT NULL," +
                "created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
                "updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW())");

        db.execute("CREATE OR REPLACE FUNCTION set_updated_at() RETURNS TRIGGER AS $$ BEGIN NEW.updated_at := NOW(); RETURN NEW; END; $$ LANGUAGE plpgsql;");
        db.execute("DROP TRIGGER IF EXISTS trg_venda_updated ON \"Venda\"");
        db.execute("CREATE TRIGGER trg_venda_updated BEFORE UPDATE ON \"Venda\" FOR EACH ROW EXECUTE FUNCTION set_updated_at();");
        db.execute("DROP TRIGGER IF EXISTS trg_cliente_updated ON \"Cliente\"");
        db.execute("CREATE TRIGGER trg_cliente_updated BEFORE UPDATE ON \"Cliente\" FOR EACH ROW EXECUTE FUNCTION set_updated_at();");

        db.execute("TRUNCATE TABLE \"Venda\" RESTART IDENTITY CASCADE");
        db.execute("TRUNCATE TABLE \"Cliente\" RESTART IDENTITY CASCADE");

        List<Integer> ids = db.query(
                "INSERT INTO \"Cliente\" (nome, email) VALUES (?, ?) RETURNING id",
                row -> row.get("id", Integer.class),
                "Cliente Teste", "cliente.teste@example.com"
        );
        clienteId = ids.get(0);

        dao = new VendaDAO(URL, USER, PASS);
        service = new VendaService(dao);
    }

    @AfterClass
    public static void afterAll() {
        if (db != null) db.close();
    }

    @Test
    public void test01_tabelasExistem() {
        String sql = "SELECT COUNT(*) AS total FROM information_schema.tables WHERE table_schema='public' AND table_name='Venda'";
        List<Long> res = db.query(sql, row -> row.get("total", Long.class));
        assertFalse(res.isEmpty());
        assertTrue(res.get(0) > 0L);
    }

    @Test
    public void test02_crudVendaDAO() {
        Venda nova = new Venda("VND-1001", clienteId, LocalDateTime.now(), new BigDecimal("250.00"));
        Venda salva = dao.save(nova);

        assertNotNull(salva.getId());
        assertNotNull(salva.getCreatedAt());
        assertNotNull(salva.getUpdatedAt());

        var opt = dao.findById(salva.getId());
        assertTrue(opt.isPresent());
        assertEquals("VND-1001", opt.get().getNumero());

        assertTrue(dao.exists(salva.getId()));

        salva.setNumero("VND-1001A");
        salva.setTotal(new BigDecimal("299.90"));
        Venda up = dao.update(salva);

        assertEquals("VND-1001A", up.getNumero());
        assertEquals(0, up.getTotal().compareTo(new BigDecimal("299.90")));
        Timestamp ct = Timestamp.valueOf(up.getCreatedAt());
        Timestamp ut = Timestamp.valueOf(up.getUpdatedAt());
        assertTrue(ut.equals(ct) || ut.after(ct));

        assertTrue(dao.findByNumero("VND-1001A").isPresent());
        List<Venda> doCliente = dao.findByCliente(clienteId);
        assertFalse(doCliente.isEmpty());

        List<Venda> todos = dao.findAll();
        assertFalse(todos.isEmpty());

        dao.deleteById(salva.getId());
        assertFalse(dao.exists(salva.getId()));
        assertFalse(dao.findById(salva.getId()).isPresent());
    }

    @Test
    public void test03_crudViaService() {
        Venda v = new Venda("VND-2001", clienteId, LocalDateTime.now(), new BigDecimal("150.00"));
        v = service.criar(v);
        assertNotNull(v.getId());

        v.setTotal(new BigDecimal("199.99"));
        v = service.atualizar(v);
        assertEquals(0, v.getTotal().compareTo(new BigDecimal("199.99")));

        assertTrue(service.buscarPorNumero("VND-2001").isPresent());
        assertFalse(service.listarPorCliente(clienteId).isEmpty());

        service.remover(v.getId());
        assertFalse(service.buscarPorId(v.getId()).isPresent());
    }
}
