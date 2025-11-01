package org.example.infra.dao;

import org.example.infra.adapter.DatabaseAdapter;
import org.example.infra.database.JdbcDatabaseAdapter;
import org.example.domain.entities.Produto;
import org.example.domain.model.RowMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ProdutoDAO implements IProdutoDAO {
    private final DatabaseAdapter db;

    public ProdutoDAO(String url, String user, String password) {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        this.db = new JdbcDatabaseAdapter(url, props);
        this.db.connect();
    }

    // Mapper
    private static final RowMapper<Produto> MAPPER = row -> {
        Produto p = new Produto(row.get("nome", String.class), row.get("valor", BigDecimal.class));
        p.setId(row.get("id", Integer.class));
        Timestamp ct = row.get("created_at", Timestamp.class);
        Timestamp ut = row.get("updated_at", Timestamp.class);
        if (ct != null) p.setCreatedAt(ct.toLocalDateTime());
        if (ut != null) p.setUpdatedAt(ut.toLocalDateTime());
        return p;
    };

    @Override
    public Produto save(Produto entity) {
        String sql =
                "INSERT INTO produto (nome, valor) " +
                        "VALUES (?, ?) " +
                        "RETURNING id, nome, valor, created_at, updated_at";

        List<Produto> out = db.query(sql, MAPPER, entity.getNome(), entity.getValor());
        if (out.isEmpty()) throw new RuntimeException("Falha ao inserir produto.");
        Produto saved = out.get(0);

        entity.setId(saved.getId());
        entity.setCreatedAt(saved.getCreatedAt());
        entity.setUpdatedAt(saved.getUpdatedAt());
        return entity;
    }

    @Override
    public Produto update(Produto entity) {
        String sql =
                "UPDATE produto " +
                        "SET nome = ?, valor = ? " +
                        "WHERE id = ? " +
                        "RETURNING id, nome, valor, created_at, updated_at";

        List<Produto> out = db.query(sql, MAPPER, entity.getNome(), entity.getValor(), entity.getId());
        if (out.isEmpty()) throw new RuntimeException("Produto não encontrado para update: id=" + entity.getId());

        Produto updated = out.get(0);
        entity.setCreatedAt(updated.getCreatedAt());
        entity.setUpdatedAt(updated.getUpdatedAt());
        return entity;
    }

    @Override
    public Optional<Produto> findById(Integer id) {
        String sql =
                "SELECT id, nome, valor, created_at, updated_at " +
                        "FROM produto " +
                        "WHERE id = ?";

        List<Produto> list = db.query(sql, MAPPER, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public boolean exists(Integer id) {
        String sql =
                "SELECT 1 AS exists_flag " +
                        "FROM produto " +
                        "WHERE id = ? " +
                        "LIMIT 1";

        List<Integer> r = db.query(sql, row -> row.get("exists_flag", Integer.class), id);
        return !r.isEmpty();
    }

    @Override
    public List<Produto> findAll() {
        String sql =
                "SELECT id, nome, valor, created_at, updated_at " +
                        "FROM produto " +
                        "ORDER BY id";

        return db.query(sql, MAPPER);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        db.execute(sql, id);
    }
}
