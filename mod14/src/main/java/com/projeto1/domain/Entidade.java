package main.java.com.projeto1.domain;

import java.util.Objects;
import java.util.UUID;

public abstract class Entidade {
    private final String id;

    protected Entidade() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Entidade)) return false;
        Entidade entidade = (Entidade) o;
        return Objects.equals(this.id, entidade.id);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "%s{id=%s}".format(getClass().getSimpleName(), this.id);
    }
}
