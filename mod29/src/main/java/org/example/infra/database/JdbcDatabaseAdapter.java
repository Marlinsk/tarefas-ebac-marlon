package org.example.infra.database;

import org.example.infra.adapter.DatabaseAdapter;
import org.example.exceptions.DatabaseException;
import org.example.domain.model.ResultRow;
import org.example.domain.model.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcDatabaseAdapter implements DatabaseAdapter {
    private final String url;
    private final Properties props;
    private Connection conn;
    private boolean inTx = false;

    public JdbcDatabaseAdapter(String url, Properties props) {
        this.url = url;
        this.props = props;
    }

    @Override
    public void connect() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url, props);
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao abrir conexão JDBC", e);
        }
    }

    @Override
    public boolean isConnected() {
        try {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void begin() {
        ensureConnected();
        try {
            if (!inTx) {
                conn.setAutoCommit(false);
                inTx = true;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao iniciar transação", e);
        }
    }

    @Override
    public void commit() {
        ensureConnected();
        try {
            if (inTx) {
                conn.commit();
                conn.setAutoCommit(true);
                inTx = false;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao dar commit", e);
        }
    }

    @Override
    public void rollback() {
        ensureConnected();
        try {
            if (inTx) {
                conn.rollback();
                conn.setAutoCommit(true);
                inTx = false;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao dar rollback", e);
        }
    }

    @Override
    public int execute(String sql, Object... params) {
        ensureConnected();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            bind(ps, params);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao executar DML: " + sql, e);
        }
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> mapper, Object... params) {
        ensureConnected();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            bind(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                List<T> out = new ArrayList<>();
                ResultRow row = new JdbcResultRow(rs);
                while (rs.next()) {
                    out.add(mapper.map(row));
                }
                return out;
            }
        } catch (Exception e) {
            throw new DatabaseException("Erro ao executar SELECT: " + sql, e);
        }
    }

    private static void bind(PreparedStatement ps, Object... params) throws SQLException {
        if (params == null) return;
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }

    private void ensureConnected() {
        if (!isConnected()) connect();
    }

    @Override
    public void close() {
        try {
            if (conn != null) {
                if (inTx) rollback();
                conn.close();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao fechar conexão", e);
        }
    }

    static class JdbcResultRow implements ResultRow {
        private final ResultSet rs;

        JdbcResultRow(ResultSet rs) {
            this.rs = rs;
        }

        @Override
        public <T> T get(String column, Class<T> type) {
            try {
                Object v = rs.getObject(column);
                return type.cast(v);
            } catch (Exception e) {
                throw new DatabaseException("Erro ao ler coluna: " + column, e);
            }
        }

        @Override
        public Object get(int index) {
            try {
                return rs.getObject(index);
            } catch (SQLException e) {
                throw new DatabaseException("Erro ao ler índice: " + index, e);
            }
        }
    }
}
