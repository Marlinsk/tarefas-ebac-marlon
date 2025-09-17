package org.example.project.common.mapper;

import org.example.project.common.annotations.TableColumn;
import org.example.project.domain.model.ResultRow;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class TableColumnMapper {

    private TableColumnMapper() {}

    public static <T> T map(ResultRow row, Class<T> type) {
        try {
            T instance = type.getDeclaredConstructor().newInstance();

            Class<?> c = type;
            while (c != null && c != Object.class) {
                for (Field f : c.getDeclaredFields()) {
                    TableColumn col = f.getAnnotation(TableColumn.class);
                    if (col == null) continue;

                    String columnName = col.dbName();
                    String setterName = col.setJavaName();
                    Class<?> fieldType = f.getType();

                    Object value = read(row, columnName, fieldType);
                    Method setter = findSetter(type, setterName, fieldType);
                    if (setter != null) {
                        setter.invoke(instance, value);
                    } else {
                        f.setAccessible(true);
                        f.set(instance, value);
                    }
                }
                c = c.getSuperclass();
            }
            return instance;
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao mapear " + type.getSimpleName() + " via @TableColumn", e);
        }
    }

    private static Method findSetter(Class<?> type, String name, Class<?> paramType) {
        Class<?> c = type;
        while (c != null) {
            try { return c.getMethod(name, paramType); }
            catch (NoSuchMethodException ignored) { c = c.getSuperclass(); }
        }
        return null;
    }

    private static Object read(ResultRow row, String col, Class<?> target) {
        if (target == LocalDateTime.class) {
            Timestamp ts = row.get(col, Timestamp.class);
            return ts != null ? ts.toLocalDateTime() : null;
        }
        if (Number.class.isAssignableFrom(target) || target.isPrimitive()) {
            // Deixe o adaptador cuidar das conversões numéricas (Integer/Long/BigDecimal etc.)
            return row.get(col, (Class) target);
        }
        if (target == BigDecimal.class) {
            return row.get(col, BigDecimal.class);
        }
        return row.get(col, (Class) target);
    }
}
