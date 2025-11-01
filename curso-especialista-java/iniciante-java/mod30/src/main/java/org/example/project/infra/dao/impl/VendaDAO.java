package org.example.project.infra.dao.impl;

import org.example.project.domain.entities.Venda;
import org.example.project.domain.model.RowMapper;
import org.example.project.common.mapper.TableColumnMapper;
import org.example.project.infra.adapters.DatabaseAdapter;
import org.example.project.infra.adapters.JdbcDatabaseAdapter;
import org.example.project.infra.dao.IVendaDAO;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class VendaDAO implements IVendaDAO {

    private final DatabaseAdapter db;
    private static final String TABLE = "\"Venda\"";
    private static final RowMapper<Venda> MAPPER = row -> TableColumnMapper.map(row, Venda.class);

    public VendaDAO(String url, String user, String password) {
        Properties p = new Properties();
        p.setProperty("user", user);
        p.setProperty("password", password);
        this.db = new JdbcDatabaseAdapter(url, p) {};
        this.db.connect();
    }

    @Override
    public Venda save(Venda entity) {
        String sql = "INSERT INTO " + TABLE + " (numero, cliente_id, data_venda, total) " + "VALUES (?, ?, ?, ?) " + "RETURNING id, numero, cliente_id, data_venda, total, created_at, updated_at";
        List<Venda> out = db.query(sql, MAPPER, entity.getNumero(), entity.getClienteId(), entity.getDataVenda(), entity.getTotal());
        if (out.isEmpty()) throw new RuntimeException("Falha ao inserir venda.");
        Venda saved = out.get(0);
        entity.setId(saved.getId());
        entity.setCreatedAt(saved.getCreatedAt());
        entity.setUpdatedAt(saved.getUpdatedAt());
        return entity;
    }

    @Override
    public Venda update(Venda entity) {
        String sql = "UPDATE " + TABLE + " SET numero = ?, cliente_id = ?, data_venda = ?, total = ? " + "WHERE id = ? " + "RETURNING id, numero, cliente_id, data_venda, total, created_at, updated_at";
        List<Venda> out = db.query(sql, MAPPER, entity.getNumero(), entity.getClienteId(), entity.getDataVenda(), entity.getTotal(), entity.getId());
        if (out.isEmpty()) throw new RuntimeException("Venda não encontrada: id=" + entity.getId());
        Venda up = out.get(0);
        entity.setCreatedAt(up.getCreatedAt());
        entity.setUpdatedAt(up.getUpdatedAt());
        return entity;
    }

    @Override
    public Optional<Venda> findById(Integer id) {
        String sql = "SELECT id, numero, cliente_id, data_venda, total, created_at, updated_at " + "FROM " + TABLE + " WHERE id = ?";
        List<Venda> list = db.query(sql, MAPPER, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public boolean exists(Integer id) {
        String sql = "SELECT 1 AS exists_flag FROM " + TABLE + " WHERE id = ? LIMIT 1";
        List<Integer> r = db.query(sql, row -> row.get("exists_flag", Integer.class), id);
        return !r.isEmpty();
    }

    @Override
    public List<Venda> findAll() {
        String sql = "SELECT id, numero, cliente_id, data_venda, total, created_at, updated_at " + "FROM " + TABLE + " ORDER BY id";
        return db.query(sql, MAPPER);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM " + TABLE + " WHERE id = ?";
        db.execute(sql, id);
    }

    @Override
    public Optional<Venda> findByNumero(String numero) {
        String sql = "SELECT id, numero, cliente_id, data_venda, total, created_at, updated_at " + "FROM " + TABLE + " WHERE numero = ?";
        List<Venda> list = db.query(sql, MAPPER, numero);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<Venda> findByCliente(Integer clienteId) {
        String sql = "SELECT id, numero, cliente_id, data_venda, total, created_at, updated_at " + "FROM " + TABLE + " WHERE cliente_id = ? ORDER BY id";
        return db.query(sql, MAPPER, clienteId);
    }
}
