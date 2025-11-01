package org.example.domain.model;

@FunctionalInterface
public interface RowMapper<T> {
    T map(ResultRow row) throws Exception;
}
