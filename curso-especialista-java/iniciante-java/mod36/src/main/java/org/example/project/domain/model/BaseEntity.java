package org.example.project.domain.model;

import jakarta.persistence.*;
import org.example.project.common.annotations.BusinessKey;
import org.hibernate.proxy.HibernateProxy;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
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

    @PrePersist
    protected void onCreate() {
        final LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Class<?> thisClass = getEffectiveClass(this);
        Class<?> thatClass = getEffectiveClass(o);
        if (!thisClass.equals(thatClass)) return false;

        BaseEntity that = (BaseEntity) o;

        if (this.id != null && that.id != null) {
            return Objects.equals(this.id, that.id);
        }

        try {
            for (Field f : thisClass.getDeclaredFields()) {
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
            Class<?> clazz = getEffectiveClass(this);
            for (Field f : clazz.getDeclaredFields()) {
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

    private static Class<?> getEffectiveClass(Object entity) {
        if (entity instanceof HibernateProxy) {
            return ((HibernateProxy) entity).getHibernateLazyInitializer().getPersistentClass();
        }
        return entity.getClass();
    }
}
