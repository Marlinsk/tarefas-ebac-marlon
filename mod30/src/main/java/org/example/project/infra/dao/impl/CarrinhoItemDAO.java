package org.example.project.infra.dao.impl;

import org.example.project.domain.entities.CarrinhoItem;
import org.example.project.domain.model.RowMapper;
import org.example.project.infra.adapters.DatabaseAdapter;
import org.example.project.infra.adapters.JdbcDatabaseAdapter;
import org.example.project.common.mapper.TableColumnMapper;
import org.example.project.infra.dao.ICarrinhoItemDAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class CarrinhoItemDAO implements ICarrinhoItemDAO {

    private final DatabaseAdapter db;
    private static final String TABLE = "\"CarrinhoItem\"";

    private static final RowMapper<CarrinhoItem> MAPPER = row -> TableColumnMapper.map(row, CarrinhoItem.class);

    public CarrinhoItemDAO(String url, String user, String password) {
        Properties p = new Properties();
        p.setProperty("user", user);
        p.setProperty("password", password);
        this.db = new JdbcDatabaseAdapter(url, p) {};
        this.db.connect();
    }

    @Override
    public CarrinhoItem save(CarrinhoItem entity) {
        String sql = "INSERT INTO " + TABLE + " (carrinho_id, produto_id, quantidade, preco_unitario, subtotal) " + "VALUES (?, ?, ?, ?, ?) " + "RETURNING id, carrinho_id, produto_id, quantidade, preco_unitario, subtotal, created_at, updated_at";
        List<CarrinhoItem> out = db.query(sql, MAPPER, entity.getCarrinhoId(), entity.getProdutoId(), entity.getQuantidade(), entity.getPrecoUnitario(), entity.getSubtotal());
        if (out.isEmpty()) throw new RuntimeException("Falha ao inserir item do carrinho.");
        CarrinhoItem saved = out.get(0);
        entity.setId(saved.getId());
        entity.setCreatedAt(saved.getCreatedAt());
        entity.setUpdatedAt(saved.getUpdatedAt());
        return entity;
    }

    @Override
    public CarrinhoItem update(CarrinhoItem entity) {
        String sql = "UPDATE " + TABLE + " SET quantidade = ?, preco_unitario = ?, subtotal = ? " + "WHERE id = ? " + "RETURNING id, carrinho_id, produto_id, quantidade, preco_unitario, subtotal, created_at, updated_at";
        List<CarrinhoItem> out = db.query(sql, MAPPER, entity.getQuantidade(), entity.getPrecoUnitario(), entity.getSubtotal(), entity.getId());
        if (out.isEmpty()) throw new RuntimeException("CarrinhoItem não encontrado: id=" + entity.getId());
        CarrinhoItem up = out.get(0);
        entity.setCreatedAt(up.getCreatedAt());
        entity.setUpdatedAt(up.getUpdatedAt());
        return entity;
    }

    @Override
    public Optional<CarrinhoItem> findById(Integer id) {
        String sql = "SELECT id, carrinho_id, produto_id, quantidade, preco_unitario, subtotal, created_at, updated_at " + "FROM " + TABLE + " WHERE id = ?";
        List<CarrinhoItem> list = db.query(sql, MAPPER, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public boolean exists(Integer id) {
        String sql = "SELECT 1 AS exists_flag FROM " + TABLE + " WHERE id = ? LIMIT 1";
        List<Integer> r = db.query(sql, row -> row.get("exists_flag", Integer.class), id);
        return !r.isEmpty();
    }

    @Override
    public List<CarrinhoItem> findAll() {
        String sql = "SELECT id, carrinho_id, produto_id, quantidade, preco_unitario, subtotal, created_at, updated_at " + "FROM " + TABLE + " ORDER BY id";
        return db.query(sql, MAPPER);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM " + TABLE + " WHERE id = ?";
        db.execute(sql, id);
    }

    @Override
    public List<CarrinhoItem> findByCarrinho(Integer carrinhoId) {
        String sql = "SELECT id, carrinho_id, produto_id, quantidade, preco_unitario, subtotal, created_at, updated_at " + "FROM " + TABLE + " WHERE carrinho_id = ? ORDER BY id";
        return db.query(sql, MAPPER, carrinhoId);
    }

    @Override
    public Optional<CarrinhoItem> findByCarrinhoAndProduto(Integer carrinhoId, Integer produtoId) {
        String sql = "SELECT id, carrinho_id, produto_id, quantidade, preco_unitario, subtotal, created_at, updated_at " + "FROM " + TABLE + " WHERE carrinho_id = ? AND produto_id = ? LIMIT 1";
        List<CarrinhoItem> list = db.query(sql, MAPPER, carrinhoId, produtoId);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public void deleteByCarrinhoAndProduto(Integer carrinhoId, Integer produtoId) {
        String sql = "DELETE FROM " + TABLE + " WHERE carrinho_id = ? AND produto_id = ?";
        db.execute(sql, carrinhoId, produtoId);
    }

    @Override
    public BigDecimal sumSubtotalByCarrinho(Integer carrinhoId) {
        String sql = "SELECT COALESCE(SUM(subtotal), 0) AS total FROM " + TABLE + " WHERE carrinho_id = ?";
        List<java.math.BigDecimal> res = db.query(sql, row -> row.get("total", java.math.BigDecimal.class), carrinhoId);
        return res.isEmpty() ? java.math.BigDecimal.ZERO : res.get(0);
    }
}
