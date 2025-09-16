package org.example.infra.dao;

import org.example.infra.adapter.DatabaseAdapter;
import org.example.infra.database.JdbcDatabaseAdapter;
import org.example.domain.entities.Cliente;
import org.example.domain.model.RowMapper;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.sql.Timestamp;

public class ClienteDAO implements IClienteDAO {

    private final DatabaseAdapter db;

    public ClienteDAO(String url, String user, String password) {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        this.db = new JdbcDatabaseAdapter(url, props);
        this.db.connect();
    }

    // Mapper
    private static final RowMapper<Cliente> MAPPER = row -> {
        Cliente c = new Cliente(row.get("nome", String.class), row.get("email", String.class));
        c.setId(row.get("id", Integer.class));
        Timestamp ct = row.get("created_at", Timestamp.class);
        Timestamp ut = row.get("updated_at", Timestamp.class);
        if (ct != null) c.setCreatedAt(ct.toLocalDateTime());
        if (ut != null) c.setUpdatedAt(ut.toLocalDateTime());
        return c;
    };


    @Override
    public Cliente save(Cliente entity) {
        String sql =
                "INSERT INTO cliente (nome, email) " +
                        "VALUES (?, ?) " +
                        "RETURNING id, nome, email, created_at, updated_at";
        List<Cliente> out = db.query(sql, MAPPER, entity.getNome(), entity.getEmail());
        if (out.isEmpty()) throw new RuntimeException("Falha ao inserir cliente.");
        Cliente saved = out.get(0);
        entity.setId(saved.getId());
        entity.setCreatedAt(saved.getCreatedAt());
        entity.setUpdatedAt(saved.getUpdatedAt());
        return entity;
    }

    @Override
    public Cliente update(Cliente entity) {
        String sql =
                "UPDATE cliente " +
                        "SET nome = ?, email = ? " +
                        "WHERE id = ? " +
                        "RETURNING id, nome, email, created_at, updated_at";
        List<Cliente> out = db.query(sql, MAPPER, entity.getNome(), entity.getEmail(), entity.getId());
        if (out.isEmpty()) throw new RuntimeException("Cliente não encontrado para update: id=" + entity.getId());
        Cliente updated = out.get(0);
        entity.setCreatedAt(updated.getCreatedAt());
        entity.setUpdatedAt(updated.getUpdatedAt());
        return entity;
    }

    @Override
    public Optional<Cliente> findById(Integer id) {
        String sql =
                "SELECT id, nome, email, created_at, updated_at " +
                        "FROM cliente " +
                        "WHERE id = ?";
        List<Cliente> list = db.query(sql, MAPPER, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public boolean exists(Integer id) {
        String sql =
                "SELECT 1 AS exists_flag " +
                        "FROM cliente " +
                        "WHERE id = ? " +
                        "LIMIT 1";
        List<Integer> r = db.query(sql, row -> row.get("exists_flag", Integer.class), id);
        return !r.isEmpty();
    }

    @Override
    public List<Cliente> findAll() {
        String sql =
                "SELECT id, nome, email, created_at, updated_at " +
                        "FROM cliente " +
                        "ORDER BY id";
        return db.query(sql, MAPPER);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM cliente WHERE id = ?";
        db.execute(sql, id);
    }
}
