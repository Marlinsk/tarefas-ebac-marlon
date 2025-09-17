package org.example.project.infra.dao.impl;

import org.example.project.domain.entities.NotaFiscal;
import org.example.project.domain.model.RowMapper;
import org.example.project.common.mapper.TableColumnMapper;
import org.example.project.infra.adapters.DatabaseAdapter;
import org.example.project.infra.adapters.JdbcDatabaseAdapter;
import org.example.project.infra.dao.INotaFiscalDAO;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class NotaFiscalDAO implements INotaFiscalDAO {

    private final DatabaseAdapter db;
    private static final String TABLE = "\"NotaFiscal\"";
    private static final RowMapper<NotaFiscal> MAPPER = row -> TableColumnMapper.map(row, NotaFiscal.class);

    public NotaFiscalDAO(String url, String user, String password) {
        Properties p = new Properties();
        p.setProperty("user", user);
        p.setProperty("password", password);
        this.db = new JdbcDatabaseAdapter(url, p) {};
        this.db.connect();
    }

    @Override
    public NotaFiscal save(NotaFiscal entity) {
        String sql = "INSERT INTO " + TABLE + " (chave_acesso, numero, serie, venda_id, data_emissao, valor_total) " + "VALUES (?, ?, ?, ?, ?, ?) " + "RETURNING id, chave_acesso, numero, serie, venda_id, data_emissao, valor_total, created_at, updated_at";
        List<NotaFiscal> out = db.query(sql, MAPPER, entity.getChaveAcesso(), entity.getNumero(), entity.getSerie(), entity.getVendaId(), entity.getDataEmissao(), entity.getValorTotal());
        if (out.isEmpty()) throw new RuntimeException("Falha ao inserir nota fiscal.");
        NotaFiscal saved = out.get(0);
        entity.setId(saved.getId());
        entity.setCreatedAt(saved.getCreatedAt());
        entity.setUpdatedAt(saved.getUpdatedAt());
        return entity;
    }

    @Override
    public NotaFiscal update(NotaFiscal entity) {
        String sql = "UPDATE " + TABLE + " SET chave_acesso = ?, numero = ?, serie = ?, venda_id = ?, data_emissao = ?, valor_total = ? " + "WHERE id = ? " + "RETURNING id, chave_acesso, numero, serie, venda_id, data_emissao, valor_total, created_at, updated_at";
        List<NotaFiscal> out = db.query(sql, MAPPER, entity.getChaveAcesso(), entity.getNumero(), entity.getSerie(), entity.getVendaId(), entity.getDataEmissao(), entity.getValorTotal(), entity.getId());
        if (out.isEmpty()) throw new RuntimeException("Nota fiscal não encontrada: id=" + entity.getId());
        NotaFiscal up = out.get(0);
        entity.setCreatedAt(up.getCreatedAt());
        entity.setUpdatedAt(up.getUpdatedAt());
        return entity;
    }

    @Override
    public Optional<NotaFiscal> findById(Integer id) {
        String sql = "SELECT id, chave_acesso, numero, serie, venda_id, data_emissao, valor_total, created_at, updated_at " + "FROM " + TABLE + " WHERE id = ?";
        List<NotaFiscal> list = db.query(sql, MAPPER, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public boolean exists(Integer id) {
        String sql = "SELECT 1 AS exists_flag FROM " + TABLE + " WHERE id = ? LIMIT 1";
        List<Integer> r = db.query(sql, row -> row.get("exists_flag", Integer.class), id);
        return !r.isEmpty();
    }

    @Override
    public List<NotaFiscal> findAll() {
        String sql = "SELECT id, chave_acesso, numero, serie, venda_id, data_emissao, valor_total, created_at, updated_at " + "FROM " + TABLE + " ORDER BY id";
        return db.query(sql, MAPPER);
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM " + TABLE + " WHERE id = ?";
        db.execute(sql, id);
    }

    @Override
    public Optional<NotaFiscal> findByChaveAcesso(String chaveAcesso) {
        String sql = "SELECT id, chave_acesso, numero, serie, venda_id, data_emissao, valor_total, created_at, updated_at " + "FROM " + TABLE + " WHERE chave_acesso = ?";
        List<NotaFiscal> list = db.query(sql, MAPPER, chaveAcesso);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<NotaFiscal> findByVenda(Integer vendaId) {
        String sql = "SELECT id, chave_acesso, numero, serie, venda_id, data_emissao, valor_total, created_at, updated_at " + "FROM " + TABLE + " WHERE venda_id = ? ORDER BY id";
        return db.query(sql, MAPPER, vendaId);
    }
}
