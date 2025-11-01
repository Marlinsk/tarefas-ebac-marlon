package org.example.project.domain.model;

@FunctionalInterface
public interface RowMapper<T> {
    T map(ResultRow row) throws Exception;
}
