package org.example.project.infra.dao.impl;

import org.example.project.infra.dao.IEstoqueDAO;
import org.example.project.domain.entities.Estoque;
import org.example.project.domain.model.RowMapper;
import org.example.project.infra.adapters.DatabaseAdapter;
import org.example.project.infra.adapters.JdbcDatabaseAdapter;
import org.example.project.common.mapper.TableColumnMapper;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class EstoqueDAO implements IEstoqueDAO {

    private final DatabaseAdapter db;
    private static final String TABLE = "\"Estoque\"";

    private static final RowMapper<Estoque> MAPPER = row -> TableColumnMapper.map(row, Estoque.class);

    public EstoqueDAO(String url, String user, String password) {
        Properties p = new Properties();
        p.setProperty("user", user);
        p.setProperty("password", password);
        this.db = new JdbcDatabaseAdapter(url, p) {};
        this.db.connect();
    }

    @Override
    public Estoque save(Estoque entity) {
        String sql = "INSERT INTO " + TABLE + " (produto_id, quantidade) VALUES (?, ?) " +
                "RETURNING id, produto_id, quantidade, created_at, updated_at";
        List<Estoque> out = db.query(sql, MAPPER, entity.getProdutoId(), entity.getQuantidade());
        if (out.isEmpty()) throw new RuntimeException("Falha ao inserir estoque.");
        Estoque saved = out.get(0);
        entity.setId(saved.getId());
        entity.setCreatedAt(saved.getCreatedAt());
        entity.setUpdatedAt(saved.getUpdatedAt());
        return entity;
    }

    @Override
    public Estoque update(Estoque entity) {
        String sql = "UPDATE " + TABLE + " SET quantidade = ? WHERE id = ? " +
                "RETURNING id, produto_id, quantidade, created_at, updated_at";
        List<Estoque> out = db.query(sql, MAPPER, entity.getQuantidade(), entity.getId());
        if (out.isEmpty()) throw new RuntimeException("Estoque não encontrado: id=" + entity.getId());
        Estoque up = out.get(0);
        entity.setCreatedAt(up.getCreatedAt());
        entity.setUpdatedAt(up.getUpdatedAt());
        return entity;
    }

    @Override
    public Optional<Estoque> findById(Integer id) {
        String sql = "SELECT id, produto_id, quantidade, created_at, updated_at FROM " + TABLE + " WHERE id = ?";
        List<Estoque> list = db.query(sql, MAPPER, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public boolean exists(Integer id) {
        String sql = "SELECT 1 AS exists_flag FROM " + TABLE + " WHERE id = ? LIMIT 1";
        List<Integer> r = db.query(sql, row -> row.get("exists_flag", Integer.class), id);
        return !r.isEmpty();
    }

    @Override
    public List<Estoque> findAll() {
        String sql = "SELECT id, produto_id, quantidade, created_at, updated_at FROM " + TABLE + " ORDER BY id";
        return db.query(sql, MAPPER);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM " + TABLE + " WHERE id = ?";
        db.execute(sql, id);
    }

    @Override
    public Optional<Estoque> findByProdutoId(Integer produtoId) {
        String sql = "SELECT id, produto_id, quantidade, created_at, updated_at FROM " + TABLE + " WHERE produto_id = ?";
        List<Estoque> list = db.query(sql, MAPPER, produtoId);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public void incrementar(Integer produtoId, int delta) {
        String sql = "UPDATE " + TABLE + " SET quantidade = quantidade + ? WHERE produto_id = ?";
        db.execute(sql, delta, produtoId);
    }

    @Override
    public void decrementar(Integer produtoId, int delta) {
        String sql = "UPDATE " + TABLE + " SET quantidade = GREATEST(quantidade - ?, 0) WHERE produto_id = ?";
        db.execute(sql, delta, produtoId);
    }
}
