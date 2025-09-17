package org.example.project.infra.dao.impl;

import org.example.project.domain.entities.Carrinho;
import org.example.project.domain.model.RowMapper;
import org.example.project.infra.adapters.DatabaseAdapter;
import org.example.project.infra.adapters.JdbcDatabaseAdapter;
import org.example.project.common.mapper.TableColumnMapper;
import org.example.project.infra.dao.ICarrinhoDAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class CarrinhoDAO implements ICarrinhoDAO {

    private final DatabaseAdapter db;
    private static final String TABLE = "\"Carrinho\"";

    private static final RowMapper<Carrinho> MAPPER = row -> TableColumnMapper.map(row, Carrinho.class);

    public CarrinhoDAO(String url, String user, String password) {
        Properties p = new Properties();
        p.setProperty("user", user);
        p.setProperty("password", password);
        this.db = new JdbcDatabaseAdapter(url, p) {};
        this.db.connect();
    }

    @Override
    public Carrinho save(Carrinho entity) {
        String sql = "INSERT INTO " + TABLE + " (codigo, cliente_id, status, total) " + "VALUES (?, ?, ?, ?) " + "RETURNING id, codigo, cliente_id, status, total, created_at, updated_at";
        List<Carrinho> out = db.query(sql, MAPPER, entity.getCodigo(), entity.getClienteId(), entity.getStatus(), entity.getTotal());
        if (out.isEmpty()) throw new RuntimeException("Falha ao inserir carrinho.");
        Carrinho saved = out.get(0);
        entity.setId(saved.getId());
        entity.setCreatedAt(saved.getCreatedAt());
        entity.setUpdatedAt(saved.getUpdatedAt());
        return entity;
    }

    @Override
    public Carrinho update(Carrinho entity) {
        String sql = "UPDATE " + TABLE + " SET codigo = ?, cliente_id = ?, status = ?, total = ? " + "WHERE id = ? " + "RETURNING id, codigo, cliente_id, status, total, created_at, updated_at";
        List<Carrinho> out = db.query(sql, MAPPER, entity.getCodigo(), entity.getClienteId(), entity.getStatus(), entity.getTotal(), entity.getId());
        if (out.isEmpty()) throw new RuntimeException("Carrinho não encontrado: id=" + entity.getId());
        Carrinho up = out.get(0);
        entity.setCreatedAt(up.getCreatedAt());
        entity.setUpdatedAt(up.getUpdatedAt());
        return entity;
    }

    @Override
    public Optional<Carrinho> findById(Integer id) {
        String sql = "SELECT id, codigo, cliente_id, status, total, created_at, updated_at " + "FROM " + TABLE + " WHERE id = ?";
        List<Carrinho> list = db.query(sql, MAPPER, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public boolean exists(Integer id) {
        String sql = "SELECT 1 AS exists_flag FROM " + TABLE + " WHERE id = ? LIMIT 1";
        List<Integer> r = db.query(sql, row -> row.get("exists_flag", Integer.class), id);
        return !r.isEmpty();
    }

    @Override
    public List<Carrinho> findAll() {
        String sql = "SELECT id, codigo, cliente_id, status, total, created_at, updated_at " + "FROM " + TABLE + " ORDER BY id";
        return db.query(sql, MAPPER);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM " + TABLE + " WHERE id = ?";
        db.execute(sql, id);
    }

    @Override
    public Optional<Carrinho> findByCodigo(String codigo) {
        String sql = "SELECT id, codigo, cliente_id, status, total, created_at, updated_at " + "FROM " + TABLE + " WHERE codigo = ?";
        List<Carrinho> list = db.query(sql, MAPPER, codigo);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<Carrinho> findByCliente(Integer clienteId) {
        String sql = "SELECT id, codigo, cliente_id, status, total, created_at, updated_at " + "FROM " + TABLE + " WHERE cliente_id = ? ORDER BY id";
        return db.query(sql, MAPPER, clienteId);
    }

    @Override
    public void atualizarTotal(Integer carrinhoId, BigDecimal total) {
        String sql = "UPDATE " + TABLE + " SET total = ? WHERE id = ?";
        db.execute(sql, total, carrinhoId);
    }
}
