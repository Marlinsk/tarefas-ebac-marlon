package main.java.com.projeto1.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public abstract class Entidade {
    private final String id;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected Entidade() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    protected void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Entidade)) return false;
        Entidade entidade = (Entidade) o;
        return Objects.equals(this.id, entidade.id);
    }

    @Override
    public final int hashCode() { return Objects.hash(this.id); }

    @Override
    public String toString() {
        return "%s{id=%s}".format(getClass().getSimpleName(), this.id);
    }
}
