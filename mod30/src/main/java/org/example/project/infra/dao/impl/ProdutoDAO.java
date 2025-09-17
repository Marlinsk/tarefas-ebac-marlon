package org.example.project.infra.dao.impl;

import org.example.project.infra.dao.IProdutoDAO;
import org.example.project.domain.entities.Produto;
import org.example.project.domain.model.RowMapper;
import org.example.project.infra.adapters.DatabaseAdapter;
import org.example.project.infra.adapters.JdbcDatabaseAdapter;
import org.example.project.common.mapper.TableColumnMapper;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class ProdutoDAO implements IProdutoDAO {

    private final DatabaseAdapter db;
    private static final String TABLE = "\"Produto\"";

    private static final RowMapper<Produto> MAPPER = row -> TableColumnMapper.map(row, Produto.class);

    public ProdutoDAO(String url, String user, String password) {
        Properties p = new Properties();
        p.setProperty("user", user);
        p.setProperty("password", password);
        this.db = new JdbcDatabaseAdapter(url, p) {};
        this.db.connect();
    }

    @Override
    public Produto save(Produto entity) {
        String sql = "INSERT INTO " + TABLE + " (nome, codigo, descricao, valor) " + "VALUES (?, ?, ?, ?) " + "RETURNING id, nome, codigo, descricao, valor, created_at, updated_at";
        List<Produto> out = db.query(sql, MAPPER, entity.getNome(), entity.getCodigo(), entity.getDescricao(), entity.getValor());
        if (out.isEmpty()) throw new RuntimeException("Falha ao inserir produto.");
        Produto saved = out.get(0);
        entity.setId(saved.getId());
        entity.setCreatedAt(saved.getCreatedAt());
        entity.setUpdatedAt(saved.getUpdatedAt());
        return entity;
    }

    @Override
    public Produto update(Produto entity) {
        String sql = "UPDATE " + TABLE + " SET nome = ?, codigo = ?, descricao = ?, valor = ? " + "WHERE id = ? " + "RETURNING id, nome, codigo, descricao, valor, created_at, updated_at";
        List<Produto> out = db.query(sql, MAPPER, entity.getNome(), entity.getCodigo(), entity.getDescricao(), entity.getValor(), entity.getId());
        if (out.isEmpty()) throw new RuntimeException("Produto não encontrado: id=" + entity.getId());
        Produto up = out.get(0);
        entity.setCreatedAt(up.getCreatedAt());
        entity.setUpdatedAt(up.getUpdatedAt());
        return entity;
    }

    @Override
    public Optional<Produto> findById(Integer id) {
        String sql = "SELECT id, nome, codigo, descricao, valor, created_at, updated_at FROM " + TABLE + " WHERE id = ?";
        List<Produto> list = db.query(sql, MAPPER, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public boolean exists(Integer id) {
        String sql = "SELECT 1 AS exists_flag FROM " + TABLE + " WHERE id = ? LIMIT 1";
        List<Integer> r = db.query(sql, row -> row.get("exists_flag", Integer.class), id);
        return !r.isEmpty();
    }

    @Override
    public List<Produto> findAll() {
        String sql = "SELECT id, nome, codigo, descricao, valor, created_at, updated_at FROM " + TABLE + " ORDER BY id";
        return db.query(sql, MAPPER);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM " + TABLE + " WHERE id = ?";
        db.execute(sql, id);
    }

    @Override
    public Optional<Produto> findByCodigo(String codigo) {
        String sql = "SELECT id, nome, codigo, descricao, valor, created_at, updated_at FROM " + TABLE + " WHERE codigo = ?";
        List<Produto> list = db.query(sql, MAPPER, codigo);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }
}
