package org.example.project.test;

import org.example.project.domain.entities.NotaFiscal;
import org.example.project.domain.entities.Venda;
import org.example.project.infra.adapters.DatabaseAdapter;
import org.example.project.infra.adapters.JdbcDatabaseAdapter;
import org.example.project.infra.dao.INotaFiscalDAO;
import org.example.project.infra.dao.impl.NotaFiscalDAO;
import org.example.project.infra.dao.impl.VendaDAO;
import org.example.project.infra.services.impl.NotaFiscalService;
import org.junit.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class NotaFiscalDAOTest {

    private static DatabaseAdapter db;
    private static INotaFiscalDAO dao;
    private static NotaFiscalService service;

    private static final String URL = System.getenv().getOrDefault("PG_URL", "jdbc:postgresql://localhost:5432/ebac-courses-java-backend");
    private static final String USER = System.getenv().getOrDefault("PG_USER", "postgres");
    private static final String PASS = System.getenv().getOrDefault("PG_PASS", "postgres");

    private static Integer clienteId;
    private static Integer vendaId;

    @BeforeClass
    public static void beforeAll() {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        db = new JdbcDatabaseAdapter(URL, props);
        db.connect();
        assertTrue(db.isConnected());

        db.execute("CREATE TABLE IF NOT EXISTS \"Cliente\" (" +
                "id SERIAL PRIMARY KEY, nome TEXT NOT NULL, cpf TEXT UNIQUE, email TEXT UNIQUE," +
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

        db.execute("CREATE TABLE IF NOT EXISTS \"Venda\" (" +
                "id SERIAL PRIMARY KEY," +
                "numero TEXT UNIQUE NOT NULL," +
                "cliente_id INT NOT NULL REFERENCES \"Cliente\"(id)," +
                "data_venda TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
                "total NUMERIC(18,2) NOT NULL," +
                "created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
                "updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW())");

        db.execute("CREATE TABLE IF NOT EXISTS \"NotaFiscal\" (" +
                "id SERIAL PRIMARY KEY," +
                "chave_acesso TEXT UNIQUE NOT NULL," +
                "numero TEXT NOT NULL," +
                "serie TEXT NOT NULL," +
                "venda_id INT NOT NULL REFERENCES \"Venda\"(id)," +
                "data_emissao TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
                "valor_total NUMERIC(18,2) NOT NULL," +
                "created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()," +
                "updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW())");

        db.execute("CREATE OR REPLACE FUNCTION set_updated_at() RETURNS TRIGGER AS $$ BEGIN NEW.updated_at := NOW(); RETURN NEW; END; $$ LANGUAGE plpgsql;");
        db.execute("DROP TRIGGER IF EXISTS trg_venda_updated ON \"Venda\"");
        db.execute("CREATE TRIGGER trg_venda_updated BEFORE UPDATE ON \"Venda\" FOR EACH ROW EXECUTE FUNCTION set_updated_at();");
        db.execute("DROP TRIGGER IF EXISTS trg_nf_updated ON \"NotaFiscal\"");
        db.execute("CREATE TRIGGER trg_nf_updated BEFORE UPDATE ON \"NotaFiscal\" FOR EACH ROW EXECUTE FUNCTION set_updated_at();");
        db.execute("DROP TRIGGER IF EXISTS trg_cliente_updated ON \"Cliente\"");
        db.execute("CREATE TRIGGER trg_cliente_updated BEFORE UPDATE ON \"Cliente\" FOR EACH ROW EXECUTE FUNCTION set_updated_at();");

        db.execute("TRUNCATE TABLE \"NotaFiscal\" RESTART IDENTITY CASCADE");
        db.execute("TRUNCATE TABLE \"Venda\" RESTART IDENTITY CASCADE");
        db.execute("TRUNCATE TABLE \"Cliente\" RESTART IDENTITY CASCADE");

        List<Integer> cids = db.query(
                "INSERT INTO \"Cliente\" (nome, email) VALUES (?, ?) RETURNING id",
                row -> row.get("id", Integer.class),
                "Cliente NF", "cliente.nf@example.com");
        clienteId = cids.get(0);

        VendaDAO vendaDAO = new VendaDAO(URL, USER, PASS);
        Venda venda = new Venda("VND-NF-001", clienteId, LocalDateTime.now(), new BigDecimal("500.00"));
        venda = vendaDAO.save(venda);
        vendaId = venda.getId();

        dao = new NotaFiscalDAO(URL, USER, PASS);
        service = new NotaFiscalService(dao);
    }

    @AfterClass
    public static void afterAll() {
        if (db != null) db.close();
    }

    @Test
    public void test01_tabelaExiste() {
        String sql = "SELECT COUNT(*) AS total FROM information_schema.tables WHERE table_schema='public' AND table_name='NotaFiscal'";
        List<Long> res = db.query(sql, row -> row.get("total", Long.class));
        assertFalse(res.isEmpty());
        assertTrue(res.get(0) > 0L);
    }

    @Test
    public void test02_crudNotaFiscalDAO() {
        NotaFiscal nf = new NotaFiscal("CHAVE-123-XYZ", "000001", "1", vendaId, LocalDateTime.now(), new BigDecimal("500.00"));
        NotaFiscal salva = dao.save(nf);

        assertNotNull(salva.getId());
        assertNotNull(salva.getCreatedAt());
        assertNotNull(salva.getUpdatedAt());

        var opt = dao.findById(salva.getId());
        assertTrue(opt.isPresent());
        assertEquals("CHAVE-123-XYZ", opt.get().getChaveAcesso());

        assertTrue(dao.exists(salva.getId()));

        salva.setNumero("000002");
        salva.setValorTotal(new BigDecimal("499.90"));
        NotaFiscal up = dao.update(salva);

        assertEquals("000002", up.getNumero());
        assertEquals(0, up.getValorTotal().compareTo(new BigDecimal("499.90")));

        Timestamp ct = Timestamp.valueOf(up.getCreatedAt());
        Timestamp ut = Timestamp.valueOf(up.getUpdatedAt());
        assertTrue(ut.equals(ct) || ut.after(ct));

        assertTrue(dao.findByChaveAcesso("CHAVE-123-XYZ").isPresent());
        assertFalse(dao.findByVenda(vendaId).isEmpty());

        assertFalse(dao.findAll().isEmpty());

        dao.deleteById(salva.getId());
        assertFalse(dao.exists(salva.getId()));
        assertFalse(dao.findById(salva.getId()).isPresent());
    }

    @Test
    public void test03_crudViaService() {
        NotaFiscal nf = new NotaFiscal("CHAVE-ABC-999", "000010", "1",
                vendaId, LocalDateTime.now(), new BigDecimal("250.00"));
        nf = service.criar(nf);
        assertNotNull(nf.getId());

        nf.setValorTotal(new BigDecimal("199.99"));
        nf = service.atualizar(nf);
        assertEquals(0, nf.getValorTotal().compareTo(new BigDecimal("199.99")));

        assertTrue(service.buscarPorChaveAcesso("CHAVE-ABC-999").isPresent());
        assertFalse(service.listarPorVenda(vendaId).isEmpty());

        service.remover(nf.getId());
        assertFalse(service.buscarPorId(nf.getId()).isPresent());
    }
}
