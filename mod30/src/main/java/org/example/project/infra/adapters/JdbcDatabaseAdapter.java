package org.example.project.infra.adapters;

import org.example.project.common.exceptions.DatabaseException;
import org.example.project.domain.model.ResultRow;
import org.example.project.domain.model.RowMapper;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcDatabaseAdapter implements DatabaseAdapter {
    private final String url;
    private final Properties props;
    private Connection conn;

    public JdbcDatabaseAdapter(String url, Properties props) {
        this.url = url;
        this.props = props;
    }

    @Override
    public void connect() {
        try {
            this.conn = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao abrir conexão JDBC", e);
        }
    }

    @Override
    public boolean isConnected() {
        try { return conn != null && !conn.isClosed(); }
        catch (SQLException e) { return false; }
    }

    @Override
    public void begin() {

    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public void close() {
        try { if (conn != null) conn.close(); }
        catch (SQLException ignored) {}
    }

    @Override
    public int execute(String sql, Object... params) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            bind(ps, params);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao executar DML: " + sql, e);
        }
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> mapper, Object... params) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            bind(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                List<T> out = new ArrayList<>();
                while (rs.next()) {
                    T obj = mapper.map(new JdbcResultRow(rs));
                    out.add(obj);
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

    private static final class JdbcResultRow implements ResultRow {
        private final ResultSet rs;

        JdbcResultRow(ResultSet rs) { this.rs = rs; }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T get(String column, Class<T> type) {
            try {
                Object v = rs.getObject(column);
                return (T) convert(v, type);
            } catch (SQLException e) {
                throw new DatabaseException("Erro ao ler coluna: " + column, e);
            }
        }

        @Override
        public Object get(int index) {
            try { return rs.getObject(index); }
            catch (SQLException e) {
                throw new DatabaseException("Erro ao ler índice: " + index, e);
            }
        }

        private static Object convert(Object v, Class<?> to) {
            if (v == null) return null;

            if (to == LocalDateTime.class) {
                if (v instanceof Timestamp) {
                    Timestamp ts = (Timestamp) v;
                    return ts.toLocalDateTime();
                }
                if (v instanceof java.sql.Date) {
                    java.sql.Date d = (java.sql.Date) v;
                    return d.toLocalDate().atStartOfDay();
                }
            }

            if (to == BigDecimal.class) {
                if (v instanceof BigDecimal) {
                    return (BigDecimal) v;
                }
                if (v instanceof Number) {
                    Number n = (Number) v;
                    return new BigDecimal(n.toString());
                }
                return new BigDecimal(v.toString());
            }

            if (to == Integer.class || to == int.class) {
                if (v instanceof Number) {
                    Number n = (Number) v;
                    return n.intValue();
                }
            }
            if (to == Long.class || to == long.class) {
                if (v instanceof Number) {
                    Number n = (Number) v;
                    return n.longValue();
                }
            }

            if (to == Boolean.class || to == boolean.class) {
                if (v instanceof Boolean) {
                    return (Boolean) v;
                }
                if (v instanceof Number) {
                    Number n = (Number) v;
                    return n.intValue() != 0;
                }
                return Boolean.parseBoolean(v.toString());
            }

            if (to == String.class) {
                return v.toString();
            }

            if (to.isInstance(v)) {
                return v;
            }

            return to.cast(v);
        }

    }
}
