package org.example.infra.adapter;

import org.example.domain.model.RowMapper;

import java.util.List;

public interface DatabaseAdapter extends AutoCloseable {
    void connect();
    boolean isConnected();
    void begin();
    void commit();
    void rollback();
    int execute(String sql, Object... params);
    <T> List<T> query(String sql, RowMapper<T> mapper, Object... params);

    @Override
    void close();
}
