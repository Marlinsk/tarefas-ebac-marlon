package org.example.project.domain.model;

import org.example.project.common.annotations.BusinessKey;
import org.example.project.common.annotations.DomainEntity;
import org.example.project.common.annotations.TableColumn;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Objects;

@DomainEntity
public abstract class BaseEntity {
    @TableColumn(dbName = "id", setJavaName = "setId")
    private Integer id;

    @TableColumn(dbName = "created_at", setJavaName = "setCreatedAt")
    private LocalDateTime createdAt;

    @TableColumn(dbName = "updated_at", setJavaName = "setUpdatedAt")
    private LocalDateTime updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        if (this.id != null && that.id != null) {
            return Objects.equals(this.id, that.id);
        }

        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field f : fields) {
                if (f.getAnnotation(BusinessKey.class) != null) {
                    f.setAccessible(true);
                    Object a = f.get(this);
                    Object b = f.get(that);
                    if (!Objects.equals(a, b)) return false;
                }
            }
            return true;
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Erro ao comparar @BusinessKey", e);
        }
    }

    @Override
    public int hashCode() {
        if (id != null) return Objects.hash(id);

        try {
            int result = 1;
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field f : fields) {
                if (f.getAnnotation(BusinessKey.class) != null) {
                    f.setAccessible(true);
                    Object v = f.get(this);
                    result = 31 * result + Objects.hashCode(v);
                }
            }
            return result;
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Erro ao calcular hash @BusinessKey", e);
        }
    }
}
