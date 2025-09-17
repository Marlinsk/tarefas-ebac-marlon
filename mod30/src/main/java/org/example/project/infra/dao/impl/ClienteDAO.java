package org.example.project.infra.dao.impl;

import org.example.project.infra.dao.IClienteDAO;
import org.example.project.domain.entities.Cliente;
import org.example.project.domain.model.RowMapper;
import org.example.project.infra.adapters.DatabaseAdapter;
import org.example.project.infra.adapters.JdbcDatabaseAdapter;
import org.example.project.common.mapper.TableColumnMapper;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ClienteDAO implements IClienteDAO {

    private final DatabaseAdapter db;
    private static final String TABLE = "\"Cliente\"";

    private static final RowMapper<Cliente> MAPPER = row -> TableColumnMapper.map(row, Cliente.class);

    public ClienteDAO(String url, String user, String password) {
        Properties p = new Properties();
        p.setProperty("user", user);
        p.setProperty("password", password);
        this.db = new JdbcDatabaseAdapter(url, p);
        this.db.connect();
    }

    public Cliente save(Cliente entity) {
        String sql =
                "INSERT INTO " + TABLE + " (nome, cpf, email) " +
                        "VALUES (?, ?, ?) " +
                        "RETURNING id, nome, cpf, email, created_at, updated_at";
        List<Cliente> out = db.query(sql, MAPPER,
                entity.getNome(), entity.getCpf(), entity.getEmail());
        if (out.isEmpty()) throw new RuntimeException("Falha ao inserir cliente.");
        Cliente saved = out.get(0);
        entity.setId(saved.getId());
        entity.setCreatedAt(saved.getCreatedAt());
        entity.setUpdatedAt(saved.getUpdatedAt());
        return entity;
    }

    public Cliente update(Cliente entity) {
        String sql =
                "UPDATE " + TABLE + " SET nome = ?, cpf = ?, email = ? WHERE id = ? " +
                        "RETURNING id, nome, cpf, email, created_at, updated_at";
        List<Cliente> out = db.query(sql, MAPPER,
                entity.getNome(), entity.getCpf(), entity.getEmail(), entity.getId());
        if (out.isEmpty()) throw new RuntimeException("Cliente não encontrado: id=" + entity.getId());
        Cliente up = out.get(0);
        entity.setCreatedAt(up.getCreatedAt());
        entity.setUpdatedAt(up.getUpdatedAt());
        return entity;
    }

    public Optional<Cliente> findById(Integer id) {
        String sql = "SELECT id, nome, cpf, email, created_at, updated_at FROM " + TABLE + " WHERE id = ?";
        List<Cliente> list = db.query(sql, MAPPER, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public boolean exists(Integer id) {
        String sql = "SELECT 1 AS exists_flag FROM " + TABLE + " WHERE id = ? LIMIT 1";
        List<Integer> r = db.query(sql, row -> row.get("exists_flag", Integer.class), id);
        return !r.isEmpty();
    }

    public List<Cliente> findAll() {
        String sql = "SELECT id, nome, cpf, email, created_at, updated_at FROM " + TABLE + " ORDER BY id";
        return db.query(sql, MAPPER);
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM " + TABLE + " WHERE id = ?";
        db.execute(sql, id);
    }
}
