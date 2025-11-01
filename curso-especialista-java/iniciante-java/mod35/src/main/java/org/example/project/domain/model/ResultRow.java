package org.example.project.domain.model;

public interface ResultRow {
    <T> T get(String column, Class<T> type);
    Object get(int index);
}
